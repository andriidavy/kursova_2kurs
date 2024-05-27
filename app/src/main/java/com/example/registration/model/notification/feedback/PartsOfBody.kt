package com.example.registration.model.notification.feedback

import com.google.gson.annotations.SerializedName

data class PartsOfBody(
    @SerializedName("textmessage") val textMessage: String? = null,
    @SerializedName("htmlmessage") val htmlMessage: String? = null
)
