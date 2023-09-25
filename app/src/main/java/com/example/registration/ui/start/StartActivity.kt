package com.example.registration.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.registration.R
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.ToastObj
import com.example.registration.ui.customer.CustomerActivity
import com.example.registration.ui.employee.EmployeeActivity
import com.example.registration.ui.login.LoginViewModel
import com.example.registration.ui.manager.ManagerActivity
import com.example.registration.ui.start.loginscreens.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {

    private val viewModel by viewModels<LoginViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen(login())
        }
    }

    private fun login(): (String, String, Int) -> Unit {
        return { email, password, num ->
            lifecycleScope.launch {
                viewModel.login(email, password, num)?.collect { loginResult ->
                    loginResult.onSuccess { user ->
                        when (num) {
                            0 -> this@StartActivity.startActivity(
                                Intent(
                                    this@StartActivity,
                                    CustomerActivity::class.java
                                )
                            )

                            1 -> this@StartActivity.startActivity(
                                Intent(
                                    this@StartActivity,
                                    EmployeeActivity::class.java
                                )
                            )

                            2 -> this@StartActivity.startActivity(
                                Intent(
                                    this@StartActivity,
                                    ManagerActivity::class.java
                                )
                            )
                        }

                        // установка ID користувача при вході
                        dataStoreViewModel.storeUserId(user.id)

                        ToastObj.longToastMake(
                            getString(
                                R.string.success_log,
                                user.name,
                                user.surname
                            ), this@StartActivity
                        )
                    }
                    loginResult.onFailure {
                        ToastObj.shortToastMake(getString(R.string.invalid_log), this@StartActivity)
                    }
                }
            }
        }
    }
}