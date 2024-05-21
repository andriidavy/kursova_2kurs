package com.example.registration.model.places.likes

import com.google.gson.annotations.SerializedName

data class AddLikeForPlaceData(
    @SerializedName("placeId")
    val placeId: String,
    @SerializedName("userId")
    val userId: String
)