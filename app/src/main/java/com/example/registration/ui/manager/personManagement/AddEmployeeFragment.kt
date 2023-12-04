package com.example.registration.ui.manager.personManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.registration.R
import com.example.registration.databinding.FragmentAddEmployeeBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEmployeeFragment : Fragment() {

    private lateinit var binding: FragmentAddEmployeeBinding
    private val viewModel by viewModels<AddEmployeeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddEmployeeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        buttonAdd.setOnClickListener {
            val name: String = etName.text.toString().trim()
            val surname: String = etSurname.text.toString().trim()
            val email: String = etEmail.text.toString().trim()
            val password: String = etPassword.text.toString().trim()
            val repPassword: String = etRepPassword.text.toString().trim()
            if (name.isNotBlank() && surname.isNotBlank() && email.isNotBlank() && password.isNotBlank() && repPassword.isNotBlank()) {
                lifecycleScope.launch {
                    viewModel.addEmployee(name, surname, email, password, repPassword)
                        .collect { result ->
                            result.onSuccess { userId ->
                                ToastObj.shortToastMake(
                                    getString(R.string.success_reg_message, userId),
                                    context
                                )

                                etName.text.clear()
                                etSurname.text.clear()
                                etEmail.text.clear()
                                etPassword.text.clear()
                                etRepPassword.text.clear()
                            }
                            result.onFailure {
                                ToastObj.shortToastMake(
                                    getString(R.string.invalid_reg_message),
                                    context
                                )
                            }
                        }
                }
            } else {
                ToastObj.shortToastMake(getString(R.string.null_check), context)
            }
        }
    }
}