package com.example.registration.ui.start.login

import android.content.Context
import android.content.Intent
import com.example.registration.R
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.global.ToastObj
import com.example.registration.ui.customer.CustomerActivity
import com.example.registration.ui.employee.EmployeeActivity
import com.example.registration.ui.manager.ManagerActivity

object LoginUtils {
    suspend fun login(
        viewModel: LoginViewModel,
        dataStoreViewModel: DataStoreViewModel,
        email: String,
        password: String,
        num: Int,
        context: Context
    ) {
        viewModel.login(email, password, num)?.collect { loginResult ->
            loginResult.onSuccess { user ->
                when (num) {
                    0 -> context.startActivity(
                        Intent(
                            context,
                            CustomerActivity::class.java
                        )
                    )

                    1 -> context.startActivity(
                        Intent(
                            context,
                            EmployeeActivity::class.java
                        )
                    )

                    2 -> context.startActivity(
                        Intent(
                            context,
                            ManagerActivity::class.java
                        )
                    )
                }
                // установка ID користувача при вході
                dataStoreViewModel.storeUserId(user.id)

                ToastObj.longToastMake(
                    context.getString(
                        R.string.success_log,
                        user.name,
                        user.surname
                    ), context
                )

                loginResult.onFailure {
                    ToastObj.shortToastMake(context.getString(R.string.invalid_log), context)
                }
            }
        }
    }
}