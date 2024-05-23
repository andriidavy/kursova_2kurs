package com.example.registration.model.users.data

import com.google.gson.annotations.SerializedName

data class LocationDTO(
    @SerializedName("type")
    val type: String = "Point",
    @SerializedName("coordinates")
    val coordinates: List<Double>
)

