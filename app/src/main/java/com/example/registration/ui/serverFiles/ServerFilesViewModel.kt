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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ServerFilesViewModel @Inject constructor(
    private val filesRepository: FilesRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""
    private val userName: String = getUser()?.name ?: ""
//    private val _serverItems = MutableStateFlow<Result<List<ServerItem>>(emptyList())>
//    val serverItems: StateFlow<List<ServerItem>>
//        get() = _serverItems

    fun getFilesFromServerFolder(pathToFolder: String): Flow<Result<List<ServerItem>>> {
        val result = filesRepository.getFilesFromServerFolder(userToken, userName, pathToFolder)
        Log.d("FilesRepository", "Files list: $result")
        return result
    }

    fun deleteFile(filePath: String) {
        viewModelScope.launch {
            filesRepository.deleteFile(filePath)
            Log.d("FilesRepository", "File deleted!")
        }
    }
}