package com.example.registration.UI.customer.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.UI.login.LoginViewModel
import com.example.registration.databinding.FragmentRegistrationBinding
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.customer.CustomerApi
import com.example.registration.database.RetrofitService
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding

    private val viewModel by viewModels<CustomerRegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setObserves()
    }

    private fun setupView() = with(binding) {
        val navController = findNavController()

        buttonReg.setOnClickListener {
            val name: String = etName.text.toString().trim()
            val surname: String = etSurname.text.toString().trim()
            val email: String = etEmail.text.toString().trim()
            val password: String = etPassword.text.toString().trim()
            if (name.isNotBlank() && surname.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                viewModel.insertCustomer(name, surname, email, password)
                    .observe(viewLifecycleOwner) { insertResult ->
                        if (insertResult) {
                            navController.navigate(R.id.action_registrationFragment_to_loginFragment)

                            etName.text.clear()
                            etSurname.text.clear()
                            etEmail.text.clear()
                            etPassword.text.clear()
                        }
                    }
            } else {
                etName.error = if (name.isBlank()) "Ім'я обов'язкове" else null
                etSurname.error = if (surname.isBlank()) "Прізвище обов'язкове" else null
                etEmail.error = if (email.isBlank()) "Email обов'язковий" else null
                etPassword.error = if (password.isBlank()) "Пароль обов'язковий" else null
            }
        }
    }

    private fun setObserves() {
        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}