package com.example.registration.model.logging

data class LogEntry(
    val `log-level`: String,
    val logger: String,
    val timestamp: Long,
    val message: String,
    val exception: String? = null
)
