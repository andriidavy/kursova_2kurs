package com.example.registration.ui.serverFiles

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.registration.database.backendless.files.FilesRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.directoryItem.ServerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class ServerFilesViewModel @Inject constructor(
    private val filesRepository: FilesRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""
    private val userName: String = getUser()?.name ?: ""

    fun getFilesFromServerFolder(pathToFolder: String): Flow<Result<List<ServerItem>>> {
        val result = filesRepository.getFilesFromServerFolder(userToken, userName, pathToFolder)
        Log.d("FilesRepository", "Files list: $result")
        return result
    }

    fun createFolder(pathToCurrFolder: String, newFolderName: String) {
        viewModelScope.launch {
            filesRepository.createFolder(userName, pathToCurrFolder, newFolderName)
        }
    }

    fun deleteFile(filePath: String) {
        viewModelScope.launch {
            filesRepository.deleteFile(filePath)
            Log.d("FilesRepository", "File deleted!")
        }
    }

    fun downloadFile(fileUrl: String): Flow<Result<ResponseBody>> {
        return filesRepository.downloadFile(fileUrl, userToken)
    }

    suspend fun saveFileToDevice(responseBody: ResponseBody, fileName: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val filePath = "/storage/emulated/0/Download/$fileName"
                val file = File(filePath)
                val inputStream = responseBody.byteStream()
                val outputStream = FileOutputStream(file)
                inputStream.use { input ->
                    outputStream.use { output ->
                        val buffer = ByteArray(4 * 1024) // 4KB
                        var read: Int
                        while (input.read(buffer).also { read = it } != -1) {
                            output.write(buffer, 0, read)
                        }
                        output.flush()
                    }
                }
                true // Успешно сохранили файл
            } catch (e: Exception) {
                e.printStackTrace()
                false // Возникла ошибка при сохранении файла
            }
        }
    }
}