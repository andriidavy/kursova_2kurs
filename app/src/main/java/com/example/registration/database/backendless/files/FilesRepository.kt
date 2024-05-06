package com.example.registration.database.backendless.files

import android.util.Log
import com.example.registration.model.directoryItem.ServerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import java.net.URLEncoder
import javax.inject.Inject

class FilesRepository @Inject constructor(private val filesApi: FilesApi) {
    suspend fun createFolder(userName: String, filePath: String, newFolderName: String) {
        filesApi.createFolder(userName, filePath, newFolderName)
    }

    suspend fun deleteFile(filePath: String) {
        filesApi.deleteFile(filePath)
    }

    fun getFilesFromServerFolder(
        userToken: String,
        userName: String,
        path: String
    ): Flow<Result<List<ServerItem>>> = flow {
        try {
            val result = filesApi.getFilesFromServerFolder(userToken, userName, path)
            Log.e("FilesRepository", "Getting files successful: $result")
            emit(Result.success(result))
        } catch (e: Exception) {
            Log.e("FilesRepository", "Getting files failed", e)
            emit(Result.failure(e))
        }
    }

    fun downloadFile(fileUrl: String, userToken: String): Flow<Result<ResponseBody>> =
        flow {
            try {
                val response = filesApi.downloadFile(fileUrl, userToken)
                Log.e("FilesRepository", "Download file successful")
                emit(Result.success(response))
            } catch (e: Exception) {
                Log.e("FilesRepository", "Download file failed", e)
                emit(Result.failure(e))
            }
        }

    fun uploadFile(
        userName: String,
        file: File,
        fileName: String,
        userToken: String,
        path: String
    ): Flow<Result<String>> = flow {
        val fileRequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val filePart = MultipartBody.Part.createFormData("file", file.name, fileRequestBody)

        try {
            val response = filesApi.uploadFile(userName, path, fileName, userToken, filePart)
            if (response.isSuccessful) {
                emit(Result.success(response.body().toString()))
            } else {
                emit(Result.failure(Exception("File upload failed: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun shareFile(
        userName: String,
        file: MultipartBody.Part,
        fileName: String,
        userToken: String,
        path: String
    ): Flow<Result<String>> = flow {

        try {
            val response = filesApi.uploadFile(userName, path, fileName, userToken, file)
            if (response.isSuccessful) {
                emit(Result.success(response.body().toString()))
            } else {
                emit(Result.failure(Exception("File upload failed: ${response.message()}")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}