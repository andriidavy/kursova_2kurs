package com.example.registration.model.users.data

import com.google.gson.annotations.SerializedName

data class ImageResponseDTO(
    @SerializedName("___class")
    val className: String,
    @SerializedName("profilePhotoUrl")
    val profilePhotoUrl: String
)