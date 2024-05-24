package com.example.registration.model.friends

import com.example.registration.model.users.data.LocationDTO
import com.google.gson.annotations.SerializedName

data class FriendItem(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("inviterId")
    val inviterId: String,
    @SerializedName("invitedId")
    val invitedId: String,
    var name: String,
    var email: String,
    var location: LocationDTO,
    var distanceToMe: Double,
)