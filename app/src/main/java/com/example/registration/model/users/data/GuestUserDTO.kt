package com.example.registration.model.users.data

import com.google.gson.annotations.SerializedName

data class GuestUserDTO(
    @SerializedName("name") val name: String,
    @SerializedName("___class") val className: String
)