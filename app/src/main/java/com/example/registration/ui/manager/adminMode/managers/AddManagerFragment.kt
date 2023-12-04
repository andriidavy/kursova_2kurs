package com.example.registration.ui.manager.adminMode.managers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.registration.R
import com.example.registration.databinding.FragmentAddManagerBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddManagerFragment : Fragment() {

    private lateinit var binding: FragmentAddManagerBinding
    private val viewModel by viewModels<AddManagerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddManagerBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() = with(binding) {
        buttonAdd.setOnClickListener {
            val name: String = etName.text.toString().trim()
            val surname: String = etSurname.text.toString().trim()
            val email: String = etEmail.text.toString().trim()
            val password: String = etPassword.text.toString().trim()
            val repPassword: String = etRepPassword.text.toString().trim()

            if (name.isNotBlank() && surname.isNotBlank() && email.isNotBlank() && password.isNotBlank() && repPassword.isNotBlank()) {
                lifecycleScope.launch {
                    viewModel.addManager(name, surname, email, password, repPassword).collect { result ->
                        result.onSuccess { userId ->
                            ToastObj.longToastMake(
                                getString(
                                    R.string.success_reg_message,
                                    userId
                                ), context
                            )

                            etName.text.clear()
                            etSurname.text.clear()
                            etEmail.text.clear()
                            etPassword.text.clear()
                        }
                        result.onFailure {
                            ToastObj.shortToastMake("Менеджер з таким email вже існує!", context)
                        }
                    }

                }
            } else {
                ToastObj.shortToastMake("Всі поля мають бути заповнені!", context)
            }
        }
    }
}
