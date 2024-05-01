package com.example.registration.ui.customer.registration

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.backendless.Backendless
import com.example.registration.R
import com.example.registration.databinding.FragmentRegistrationBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.users.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel by viewModels<CustomerRegistrationViewModel>()
    private var ageDifference: Int = 0

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

        val currentDate = Calendar.getInstance()
        val selectedDate = Calendar.getInstance()
        val birthdayPicker: DatePicker = birthdayPicker
        birthdayPicker.maxDate = currentDate.timeInMillis

        birthdayPicker.init(
            birthdayPicker.year,
            birthdayPicker.month,
            birthdayPicker.dayOfMonth
        ) { _, year, monthOfYear, dayOfMonth ->
            selectedDate.set(year, monthOfYear, dayOfMonth)

            ageDifference = currentDate.get(Calendar.YEAR) - selectedDate.get(Calendar.YEAR)
        }

        buttonReg.setOnClickListener {
            val name: String = etName.text.toString().trim()
            val email: String = etEmail.text.toString().trim()
            val password: String = etPassword.text.toString().trim()
            val nationality: String = etNationality.text.toString().trim()
            val age: Int = ageDifference
            val gender: User.Gender = if (radioButtonMale.isChecked) {
                User.Gender.MALE
            } else {
                User.Gender.FEMALE
            }

            if (name.isBlank() || email.isBlank() || password.isBlank() ||
                ageDifference < 5 || nationality.isBlank() || !isEmailValid(email) || !isGenderSelected()
            ) {
                etName.error = if (name.isBlank()) getString(R.string.name_required) else null
                etEmail.error = if (email.isBlank()) getString(R.string.email_required)
                else if (!isEmailValid(email)) getString(R.string.invalid_email) else null
                etPassword.error =
                    if (password.isBlank()) getString(R.string.password_required) else null
                etNationality.error =
                    if (nationality.isBlank()) getString(R.string.nationality_required) else null
                if (ageDifference < 5) {
                    ToastObj.longToastMake(getString(R.string.min_age), context)
                }
                if (!isGenderSelected()) {
                    ToastObj.longToastMake(getString(R.string.gender_required), context)
                }
                return@setOnClickListener
            }

            lifecycleScope.launch {
                viewModel.registerUser(name, email, password, nationality, age, gender)
                    .collect { insertResult ->
                        insertResult.onSuccess { user ->
                            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)

                            ToastObj.longToastMake(
                                getString(
                                    R.string.success_reg_message,
                                    user.email
                                ), context
                            )
                        }
                        insertResult.onFailure {
                            ToastObj.longToastMake(
                                getString(R.string.invalid_reg_message, it.message),
                                context
                            )
                        }
                    }
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isGenderSelected(): Boolean = with(binding) {
        val selectedRadioButtonId = rgGender.checkedRadioButtonId
        return selectedRadioButtonId != -1
    }
}