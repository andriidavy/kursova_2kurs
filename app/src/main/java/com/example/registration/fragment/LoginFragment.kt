package com.example.registration.fragment

import android.content.Context
import android.content.SharedPreferences
import com.example.registration.repository.CustomerRepository
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentLoginBinding
import com.example.registration.repository.EmployeeRepository
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.customerApi.CustomerApi
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.employeeApi.EmployeeApi
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.login.LoginViewModel
import com.example.registration.viewmodel.login.LoginViewModelFactory

class LoginFragment : Fragment() {
    private lateinit var viewModel: LoginViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentLoginBinding.inflate(inflater)

        // Создаем экземпляр ViewModel
        val retrofitService = RetrofitService()

        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)
        val employeeApi = retrofitService.retrofit.create(EmployeeApi::class.java)
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)

        val customerRepository = CustomerRepository(customerApi)
        val employeeRepository = EmployeeRepository(employeeApi)
        val managerRepository = ManagerRepository(managerApi)

        val viewModelFactory =
            LoginViewModelFactory(customerRepository, employeeRepository, managerRepository)

        viewModel = ViewModelProvider(this, viewModelFactory)[LoginViewModel::class.java]
        binding.loginViewModel = viewModel
        binding.lifecycleOwner = this
        // Получаем NavController
        val navController = findNavController()
        // Устанавливаем NavController в ViewModel
        viewModel.setNavController(navController)
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedPreferences)


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
                if(position==0){
                    binding.textHaveNotRegistration.visibility = View.VISIBLE
                }
                else{
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


        binding.buttonLog.setOnClickListener()
        {
            when (num) {
                0 -> viewModel.loginCustomer()
                1 -> viewModel.loginEmployee()
                2 -> viewModel.loginManager()
            }
        }

        viewModel.message.observe(
            viewLifecycleOwner
        )
        { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

        return binding.root
    }
}