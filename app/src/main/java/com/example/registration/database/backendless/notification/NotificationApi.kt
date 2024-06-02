package com.example.registration.database.backendless.notification

import com.example.registration.model.device.DeviceRegistrationRequest
import com.example.registration.model.device.DeviceRegistrationResponse
import com.example.registration.model.notification.feedback.EmailRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {
        @POST("messaging/email")
        @Headers("Content-Type: application/json")
        suspend fun sendEmail(
            @Header("user-token") userToken: String?,
            @Body emailRequest: EmailRequest
        ): Response<Unit>

    @POST("messaging/registrations")
    @Headers("Content-Type: application/json")
    suspend fun registerDevice(
        @Header("user-token") userToken: String?,
        @Body request: DeviceRegistrationRequest
    ): DeviceRegistrationResponse
}