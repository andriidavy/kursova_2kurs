package com.example.registration.database.backendless.logging

import com.example.registration.model.logging.LogEntry
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

interface LoggingApi {
    @PUT("log")
    @Headers("Content-Type: application/json")
    fun logMessage(@Body logEntries: List<LogEntry>): Call<Void>
}