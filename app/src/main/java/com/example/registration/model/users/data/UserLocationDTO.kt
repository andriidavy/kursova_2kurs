package com.example.registration.model.users.data

import com.google.gson.annotations.SerializedName

data class UserLocationDTO(
    @SerializedName("user-token")
    val userToken: String,
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("location")
    val location: LocationDTO
)
