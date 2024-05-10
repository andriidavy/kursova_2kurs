package com.example.registration.model.users.data

import com.google.gson.annotations.SerializedName

data class ImageDTO(
    @SerializedName("user-token")
    val userToken: String,
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("profilePhotoUrl")
    val profilePhotoUrl: String
)