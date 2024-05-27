package com.example.registration.model.notification.feedback

import com.google.gson.annotations.SerializedName

data class EmailRequest(
    @SerializedName("subject") val subject: String,
    @SerializedName("bodyparts") val bodyParts: PartsOfBody,
    @SerializedName("to") val to: List<String>,
    @SerializedName("attachment") val attachments: List<String>? = null
)