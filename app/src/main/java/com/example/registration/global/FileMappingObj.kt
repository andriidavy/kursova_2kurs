package com.example.registration.global

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody

object FileMappingObj {
    fun createMultipartFromString(content: String, fileName: String): MultipartBody.Part {
        val requestBody = content.toRequestBody("text/plain".toMediaType())
        val finalFileName = "$fileName.txt"
        return MultipartBody.Part.createFormData("file", finalFileName, requestBody)
    }
}