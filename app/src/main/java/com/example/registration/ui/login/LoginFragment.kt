package com.example.registration.ui.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.backendless.Backendless
import com.example.registration.R
import com.example.registration.databinding.FragmentLoginBinding
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<LoginViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        // перехід на сторінку реєстрації
        textHaveNotRegistration.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        textForgetPassword.setOnClickListener {
            viewModel.restorePassword(etEmail.text.toString())
            ToastObj.longToastMake(getString(R.string.restore_password), context)
        }

        // логін
        buttonLog.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            lifecycleScope.launch {
                viewModel.login(email, password).collect { loginResult ->
                    loginResult.onSuccess {user ->
                        navController.navigate(R.id.action_loginFragment_to_userMainPageFragment)

                        // установка користувача при вході
                        dataStoreViewModel.storeUser(user)

                        ToastObj.longToastMake(getString(R.string.success_log, user.userToken), context)
                    }
                    loginResult.onFailure {
                        ToastObj.shortToastMake(getString(R.string.invalid_log), context)
                    }
                }
            }
        }
    }
}