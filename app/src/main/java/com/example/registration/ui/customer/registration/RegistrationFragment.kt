package com.example.registration.ui.customer.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentRegistrationBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel by viewModels<CustomerRegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
        setObservers()
    }

    private fun setListeners() = with(binding) {

        buttonReg.setOnClickListener {
            val name: String = etName.text.toString().trim()
            val surname: String = etSurname.text.toString().trim()
            val email: String = etEmail.text.toString().trim()
            val password: String = etPassword.text.toString().trim()
            if (name.isNotBlank() && surname.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                lifecycleScope.launch {
                    viewModel.insertCustomer(name, surname, email, password)
                        .collect { insertResult ->
                            insertResult.onSuccess {
                                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)

                                viewModel.showSuccessfulMessage()
                            }
                            insertResult.onFailure {
                                viewModel.showInvalidMessage()
                            }
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

    private fun setObservers() {
        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }
    }
}