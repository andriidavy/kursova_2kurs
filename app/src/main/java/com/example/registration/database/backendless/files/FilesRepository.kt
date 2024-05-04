package com.example.registration.database.backendless.files

import android.util.Log
import com.example.registration.model.directoryItem.ServerItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FilesRepository @Inject constructor(private val filesApi: FilesApi) {
    suspend fun createFolder(userName: String, folderName: String) {
        filesApi.createFolder(userName, folderName)
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
            Log.e("FilesRepository", "Getting files successful")
            emit(Result.success(result))
        } catch (e: Exception) {
            Log.e("FilesRepository", "Getting files failed", e)
            emit(Result.failure(e))
        }

    }
}