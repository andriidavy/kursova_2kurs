package com.example.registration.model.users.data

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("login")
    val email: String,
    @SerializedName("password")
    val password: String
)
