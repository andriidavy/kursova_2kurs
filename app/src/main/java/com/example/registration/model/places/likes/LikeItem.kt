package com.example.registration.model.places.likes

import com.google.gson.annotations.SerializedName

data class LikeItem (
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("placeId")
    val placeId: String,
    @SerializedName("userId")
    val userId: String
)