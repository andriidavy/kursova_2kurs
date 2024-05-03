package com.example.registration.ui.serverFiles

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.registration.database.backendless.files.FilesRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServerFilesViewModel @Inject constructor(
    private val filesRepository: FilesRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""
    private val userName: String = getUser()?.name ?: ""
    fun getFilesFromServerFolder(pathToFolder: String) {
        viewModelScope.launch {
            val files = filesRepository.getFilesFromServerFolder(userToken, userName, pathToFolder)
            Log.d("FilesRepository", "Files list: $files")
        }
    }
}