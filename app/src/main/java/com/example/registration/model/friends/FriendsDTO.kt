package com.example.registration.model.friends

import com.example.registration.model.users.data.LocationDTO
import com.google.gson.annotations.SerializedName

data class FriendsDTO(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("location")
    val location: LocationDTO
    )