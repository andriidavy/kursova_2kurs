package com.example.registration.model.device

data class DeviceRegistrationRequest(
    val deviceToken: String,
    val deviceId: String,
    val os: String,
    val osVersion: String,
    val channels: List<String>? = null,
    val expiration: Long? = null
)
