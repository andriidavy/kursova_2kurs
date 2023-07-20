package com.example.registration.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentLoginBinding
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController

    private val viewModel by viewModels<LoginViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()

    private var num: Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
        setObservers()
    }

    private fun setupViews() = with(binding) {
        navController = findNavController()

        //set Spinner
        val users = arrayOf("Customer", "Employee", "Manager")
        val spinner = spinnerChooseUserType
        val arrayAdapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, users) }

        spinner.apply {
            adapter = arrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    num = position
                    textHaveNotRegistration.visibility =
                        if (position == 0) View.VISIBLE else View.GONE
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // Code to perform some action when nothing is selected
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        // перехід на сторінку реєстрації
        textHaveNotRegistration.setOnClickListener()
        {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        // логін
        buttonLog.setOnClickListener()
        {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            viewModel.login(email, password, num).observe(viewLifecycleOwner) { loginResult ->
                if (loginResult) {
                    when (num) {
                        0 -> navController.navigate(R.id.action_loginFragment_to_customerMainPageFragment)
                        1 -> navController.navigate(R.id.action_loginFragment_to_employeeMainPageFragment)
                        2 -> navController.navigate(R.id.action_loginFragment_to_managerMainPageFragment)
                    }
                }
            }
        }
    }

    private fun setObservers() {
        // вивід повідомлення
        viewModel.message.observe(
            viewLifecycleOwner
        )
        { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

        // установка ID користувача при вході
        viewModel.userId.observe(
            viewLifecycleOwner
        ) { userId -> dataStoreViewModel.storeUserId(userId) }
    }
}