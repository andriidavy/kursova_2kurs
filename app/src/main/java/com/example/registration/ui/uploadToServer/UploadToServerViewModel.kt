package com.example.registration.ui.uploadToServer

import androidx.lifecycle.MutableLiveData
import com.example.registration.database.backendless.files.FilesRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import java.io.File
import javax.inject.Inject

@HiltViewModel
class UploadToServerViewModel @Inject constructor(
    private val filesRepository: FilesRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""
    private val userName: String = getUser()?.name ?: ""
    val isFileSelectedLiveData = MutableLiveData<Boolean>()

    init {
        isFileSelectedLiveData.value = false
    }

    fun setFileSelected(isSelected: Boolean) {
        isFileSelectedLiveData.value = isSelected
    }

    fun uploadFile(file: File, path: String, fileName: String): Flow<Result<String>> {
        return filesRepository.uploadFile(userName, file, fileName, userToken, path)
    }
}