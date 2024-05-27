package com.example.registration.database.backendless.notification

import android.util.Log
import com.example.registration.model.notification.feedback.EmailRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val notificationApi: NotificationApi){
    fun sendEmail(
        userToken: String,
        emailRequest: EmailRequest
    ): Flow<Result<Unit>> =
        flow {
            try {
                val sentEmail = notificationApi.sendEmail(userToken, emailRequest)
                Log.e("Email", "email: $sentEmail")
                emit(Result.success(sentEmail))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
}