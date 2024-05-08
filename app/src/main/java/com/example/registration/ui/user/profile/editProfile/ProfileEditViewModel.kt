package com.example.registration.ui.user.profile.editProfile

import com.example.registration.database.backendless.files.FilesRepository
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.data.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val filesRepository: FilesRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    fun updateUser(userToken: String, updateRequest: UserDTO): Flow<Result<UserDTO>> {
        return userRepository.updateUser(userToken, updateRequest)
    }

    fun renameFolder(userToken: String, oldName: String, name: String): Flow<Result<String>> {
        return filesRepository.renameFolder(userToken, oldName, name)
    }
}