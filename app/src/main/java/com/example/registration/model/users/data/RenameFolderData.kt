package com.example.registration.model.users.data

import com.google.gson.annotations.SerializedName

data class RenameFolderData(
    @SerializedName("current-name")
    val oldPathName: String,
    @SerializedName("newName")
    val newName: String
)
