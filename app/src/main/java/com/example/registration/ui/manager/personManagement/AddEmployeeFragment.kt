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
            val name: String = etName.text.toString()
            val surname: String = etSurname.text.toString()
            val email: String = etEmail.text.toString()
            val password: String = etPassword.text.toString()
            if (name.isNotBlank() && surname.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                lifecycleScope.launch {
                    viewModel.addEmployee(name, surname, email, password).collect { result ->
                        result.onSuccess {
                            ToastObj.shortToastMake(
                                getString(R.string.success_reg_message),
                                context
                            )

                            etName.text.clear()
                            etSurname.text.clear()
                            etEmail.text.clear()
                            etPassword.text.clear()
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