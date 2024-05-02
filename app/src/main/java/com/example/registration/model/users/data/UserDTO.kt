package com.example.registration.model.users.data

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("user-token")
    val userToken: String,
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("gender")
    val gender: String
)
