package com.example.registration.model.friends

import com.google.gson.annotations.SerializedName

data class SearchFriendItem(
    @SerializedName("objectId")
    val objectId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String
)
