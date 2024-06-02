package com.example.registration.database.backendless.notification

import com.example.registration.model.device.DeviceRegistrationRequest
import com.example.registration.model.device.DeviceRegistrationResponse
import com.example.registration.model.notification.feedback.EmailRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
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

    fun registerDevice(
        deviceToken: String,
        deviceId: String,
        os: String,
        osVersion: String,
        userToken: String? = null,
        channels: List<String>? = null,
        expiration: Long? = null
    ): Flow<Result<DeviceRegistrationResponse>> = flow {
        try {
            val request = DeviceRegistrationRequest(
                deviceToken = deviceToken,
                deviceId = deviceId,
                os = os,
                osVersion = osVersion,
                channels = channels,
                expiration = expiration
            )
            val response = notificationApi.registerDevice(userToken, request)
            emit(Result.success(response))
        } catch (e: HttpException) {
            emit(Result.failure(IOException("HTTP error: ${e.message}")))
        } catch (e: IOException) {
            emit(Result.failure(IOException("Network error: ${e.message}")))
        } catch (e: Exception) {
            emit(Result.failure(IOException("Unexpected error: ${e.message}")))
        }
    }
}