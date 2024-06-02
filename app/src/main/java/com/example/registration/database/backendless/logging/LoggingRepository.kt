package com.example.registration.database.backendless.logging

import com.example.registration.model.logging.LogEntry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class LoggingRepository @Inject constructor(private val loggingApi: LoggingApi) {
    suspend fun logMessageToBackendless(
        logLevel: String,
        logger: String,
        timestamp: Long,
        message: String,
        exception: String? = null
    ) {
        val logEntry = LogEntry(logLevel, logger, timestamp, message, exception)
        withContext(Dispatchers.IO) {
            try {
                val response = loggingApi.logMessage(listOf(logEntry)).execute()
                if (!response.isSuccessful) {
                    println("Failed to log message to Backendless: ${response.code()}")
                }
            } catch (e: IOException) {
                println("Exception occurred while logging to Backendless: $e")
            }
        }
    }
}