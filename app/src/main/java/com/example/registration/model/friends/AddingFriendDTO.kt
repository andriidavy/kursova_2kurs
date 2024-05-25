package com.example.registration.model.friends

import com.google.gson.annotations.SerializedName

data class AddingFriendDTO(
    @SerializedName("inviterId")
    val inviterId: String,
    @SerializedName("invitedId")
    val invitedId: String,
    @SerializedName("status")
    val status: String
)