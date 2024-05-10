package com.example.registration.global

import android.app.Activity
import android.net.Uri
import android.provider.OpenableColumns
import androidx.fragment.app.FragmentActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

object FileMappingObj {
    fun createMultipartFromString(content: String, fileName: String): MultipartBody.Part {
        val requestBody = content.toRequestBody("text/plain".toMediaType())
        val finalFileName = "$fileName.txt"
        return MultipartBody.Part.createFormData("file", finalFileName, requestBody)
    }

    fun getFileFromUri(uri: Uri, activity: FragmentActivity): File {
        val inputStream = activity.contentResolver.openInputStream(uri)
        val outputFile = File(activity.cacheDir, "selected_file")
        inputStream?.copyTo(outputFile.outputStream())
        inputStream?.close()
        return outputFile
    }

    fun getFileName(uri: Uri, activity: FragmentActivity): String {
        var filename = ""
        try {
            val cursor = activity.contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val displayNameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                    filename = it.getString(displayNameIndex)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return filename
    }
}