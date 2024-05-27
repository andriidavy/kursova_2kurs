package com.example.registration.database.backendless.notification

import android.util.Log
import com.example.registration.model.notification.feedback.EmailRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(private val notificationApi: NotificationApi) {
    fun sendEmail(
        userToken: String?,
        emailRequest: EmailRequest
    ): Flow<Result<Unit>> = flow {
        try {
            val response = notificationApi.sendEmail(userToken, emailRequest)
            if (response.isSuccessful) {
                emit(Result.success(Unit))
            } else {
                emit(Result.failure(Exception("Failed to send email: ${response.errorBody()?.string()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}