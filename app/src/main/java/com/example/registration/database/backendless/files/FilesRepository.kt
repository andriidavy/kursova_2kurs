package com.example.registration.database.backendless.files

import com.example.registration.model.directoryItem.ServerItem
import javax.inject.Inject

class FilesRepository @Inject constructor(private val filesApi: FilesApi) {
    suspend fun createFolder(userName: String, folderName: String) {
        filesApi.createFolder(userName, folderName)
    }

    suspend fun getFilesFromServerFolder(
        userToken: String,
        userName: String,
        path: String
    ): List<ServerItem> {
        return filesApi.getFilesFromServerFolder(userToken, userName, path)
    }
}