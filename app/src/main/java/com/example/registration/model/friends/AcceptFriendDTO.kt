package com.example.registration.model.friends

import com.google.gson.annotations.SerializedName

data class AcceptFriendDTO (
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("status")
    val status: String
)