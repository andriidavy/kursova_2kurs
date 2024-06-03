package com.example.registration.ui.customer.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentRegistrationBinding
import com.example.registration.global.ToastObj
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
    }

    private fun setListeners() = with(binding) {
        buttonReg.setOnClickListener {
            val name: String = etName.text.toString().trim()
            val surname: String = etSurname.text.toString().trim()
            val email: String = etEmail.text.toString().trim()
            val password: String = etPassword.text.toString().trim()
            val repPassword: String = etRepPassword.text.toString().trim()

            var isValid = true

            if (name.isBlank()) {
                etName.error = getString(R.string.name_required)
                isValid = false
            } else {
                etName.error = null
            }

            if (surname.isBlank()) {
                etSurname.error = getString(R.string.surname_required)
                isValid = false
            } else {
                etSurname.error = null
            }

            if (email.isBlank()) {
                etEmail.error = getString(R.string.email_required)
                isValid = false
            } else if (!isValidEmail(email)) {
                etEmail.error = getString(R.string.invalid_email)
                isValid = false
            } else {
                etEmail.error = null
            }

            if (password.isBlank()) {
                etPassword.error = getString(R.string.password_required)
                isValid = false
            } else {
                etPassword.error = null
            }

            if (repPassword.isBlank()) {
                etRepPassword.error = getString(R.string.password_required)
                isValid = false
            } else if (password != repPassword) {
                etRepPassword.error = getString(R.string.passwords_do_not_match)
                isValid = false
            } else {
                etRepPassword.error = null
            }

            if (isValid) {
                lifecycleScope.launch {
                    viewModel.insertCustomer(name, surname, email, password, repPassword)
                        .collect { insertResult ->
                            insertResult.onSuccess { userId ->
                                findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
                                ToastObj.longToastMake(
                                    getString(R.string.success_reg_message, userId),
                                    context
                                )
                            }
                            insertResult.onFailure {
                                ToastObj.longToastMake(
                                    getString(R.string.invalid_reg_message),
                                    context
                                )
                            }
                        }
                }
            }
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}