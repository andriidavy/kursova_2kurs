package com.example.registration.ui.start.customerRegistration

import android.content.Context
import com.example.registration.R
import com.example.registration.global.ToastObj

object CustomerRegistrationUtils {
    suspend fun customerRegistration(
        name: String,
        surname: String,
        email: String,
        password: String,
        viewModel: CustomerRegistrationViewModel,
        context: Context,
        navigateBack: () -> Unit
    ) {

        viewModel.insertCustomer(name, surname, email, password)
            .collect { insertResult ->
                insertResult.onSuccess {
                    navigateBack.invoke()
                    ToastObj.longToastMake(
                        context.getString(R.string.success_reg_message),
                        context
                    )
                }
                insertResult.onFailure {
                    ToastObj.longToastMake(
                        context.getString(R.string.invalid_reg_message),
                        context
                    )
                }
            }
    }
}