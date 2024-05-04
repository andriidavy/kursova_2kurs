package com.example.registration.ui.fileManage

import androidx.lifecycle.viewModelScope
import com.example.registration.database.backendless.files.FilesRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileManageViewModel @Inject constructor(
    private val filesRepository: FilesRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userName: String = getUser()?.name ?: ""
}