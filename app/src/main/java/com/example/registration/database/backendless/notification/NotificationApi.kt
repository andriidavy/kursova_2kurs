package com.example.registration.database.backendless.notification

import com.example.registration.model.notification.feedback.EmailRequest
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface NotificationApi {
    @POST("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/messaging/email")
    @Headers("Content-Type: application/json")
    fun sendEmail(
        @Header("user-token") userToken: String,
        @Body emailRequest: EmailRequest
    )
}