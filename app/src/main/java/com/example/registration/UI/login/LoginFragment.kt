package com.example.registration.UI.login

import android.content.Context
import android.content.SharedPreferences
import com.example.registration.database.customer.CustomerRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.example.registration.R
import com.example.registration.databinding.FragmentLoginBinding
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.customer.CustomerApi
import com.example.registration.database.RetrofitService
import com.example.registration.database.employee.EmployeeApi
import com.example.registration.database.manager.ManagerApi
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var email: String
    private lateinit var password: String

    //with hilt we setup view model into fragment like this
    private val viewModel by viewModels<LoginViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentLoginBinding.inflate(inflater)

        // Получаем NavController
        val navController = findNavController()
        // Устанавливаем NavController в ViewModel
        viewModel.setNavController(navController)
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // Устанавливаем SharedPreferences для получение id пользователя с целью его использования в программе далее
        val sharedLoginPreferences: SharedPreferences =
            requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedLoginPreferences)
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //set Spinner
        val users = arrayOf("Customer", "Employee", "Manager")
        val spinner = binding.spinnerChooseUserType
        var num = -1
        val arrayAdapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, users) }
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                num = position
                if (position == 0) {
                    binding.textHaveNotRegistration.visibility = View.VISIBLE
                } else {
                    binding.textHaveNotRegistration.visibility = View.GONE
                }
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        binding.textHaveNotRegistration.setOnClickListener()
        { view: View ->
            view.findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        fun inputInit() {
            email = binding.etEmail.text.toString()
            password = binding.etPassword.text.toString()
        }

        fun loginCustomer() {
            viewModel.loginCustomer(email, password)
        }

        fun loginEmployee() {
            viewModel.loginEmployee(email, password)
        }

        fun loginManager() {
            viewModel.loginManager(email, password)
        }

        binding.buttonLog.setOnClickListener()
        {
            inputInit()
            when (num) {
                0 -> loginCustomer()
                1 -> loginEmployee()
                2 -> loginManager()
            }
        }

        viewModel.message.observe(
            viewLifecycleOwner
        )
        { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

        viewModel.userId.observe(
            viewLifecycleOwner
        ) { userId -> dataStoreViewModel.storeUserId(userId) }

        return binding.root
    }
}