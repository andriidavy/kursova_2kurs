package com.example.registration.database.backendless.files

import android.util.Log
import com.example.registration.model.directoryItem.ServerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
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
}