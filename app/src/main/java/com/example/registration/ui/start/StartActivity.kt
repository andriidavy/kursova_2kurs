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
import com.example.registration.ui.start.login.LoginViewModel
import com.example.registration.ui.manager.ManagerActivity
import com.example.registration.ui.start.login.LoginScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LoginScreen()
        }
    }
}