package com.example.registration.ui.login

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.provider.Settings;

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<LoginViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()
    private lateinit var token: String
    private lateinit var deviceId: String
    private lateinit var operationSystemVersion: String

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
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("DeviceToken", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            token = task.result
        })
        deviceId = getDeviceId(requireContext())
        operationSystemVersion = getOperatingSystemVersion()
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
                    loginResult.onSuccess { user ->
                        navController.navigate(R.id.action_loginFragment_to_userMainPageFragment)

                        // установка користувача при вході
                        dataStoreViewModel.storeUser(user)

                        ToastObj.longToastMake(
                            getString(R.string.success_log, user.userToken),
                            context
                        )
                    }
                    loginResult.onFailure {
                        ToastObj.shortToastMake(getString(R.string.invalid_log), context)
                    }
                }
            }
        }
        btDeviceReg.setOnClickListener {
            val deviceToken = token
            val deviceId = deviceId
            val os = "ANDROID"
            val osVersion = operationSystemVersion
            val channels = listOf("default")
            val expiration: Long? = null

            lifecycleScope.launch {
                viewModel.registerDevice(
                    deviceToken,
                    deviceId,
                    os,
                    osVersion,
                    channels,
                    expiration
                ).collect { result ->
                    result.onSuccess { response ->
                        Log.e("device","Device registered with ID: ${response.registrationId}")
                    }.onFailure { error ->
                        Log.e("device","Failed to register device: ${error.message}")
                    }
                }
            }
        }
    }

    private fun getDeviceId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }
    private fun getOperatingSystemVersion(): String {
        return Build.VERSION.RELEASE
    }
}