package com.example.registration.ui.login.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("login")
    val email: String,
    @SerializedName("password")
    val password: String
)
