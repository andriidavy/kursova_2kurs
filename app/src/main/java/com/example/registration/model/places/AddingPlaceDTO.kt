package com.example.registration.model.places

import com.example.registration.model.users.data.LocationDTO
import com.google.gson.annotations.SerializedName

data class AddingPlaceDTO(
    @SerializedName("description")
    val description: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("location")
    val location: LocationDTO,
    @SerializedName("createdByUserName")
    val createdByUserName: String,
    @SerializedName("placePhotoUrl")
    val placePhotoUrl: String
)