package com.example.registration.ui.user.profile.photo

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.registration.database.backendless.files.FilesRepository
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.directoryItem.ServerItem
import com.example.registration.model.users.data.ImageDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PickPhotoViewModel @Inject constructor(
    private val filesRepository: FilesRepository,
    private val userRepository: UserRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""
    private val userName: String = getUser()?.name ?: ""

    fun getAllFiles(): Flow<Result<List<ServerItem>>> {
        val result = filesRepository.getAllFiles(userToken, userName)
        Log.d("FilesRepository", "Files list: $result")
        return result
    }

    fun updateUserProfileImage(imageDTO: ImageDTO): Flow<Result<ImageDTO>> {
        return userRepository.updateUserProfileImage(userToken, imageDTO)
    }
}