package com.example.registration.model.places

import com.example.registration.model.users.data.LocationDTO
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlaceItem(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("created")
    val created: Long,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("placePhotoUrl")
    val placePhotoUrl: String,
    @SerializedName("location")
    val location: LocationDTO,
    @SerializedName("createdByUserName")
    val createdByUserName: String
) : Serializable