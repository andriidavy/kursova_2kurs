package com.example.registration.ui.user.profile

import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.data.ImageResponseDTO
import com.example.registration.model.users.data.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserProfilePageViewModel @Inject constructor(
    private val userRepository: UserRepository, datastoreRepository: DatastoreRepo
): DataStoreViewModel(datastoreRepository) {
    private val userName: String = getUser()?.name ?: ""

    fun logout(user: UserDTO?): Flow<Result<Unit>>{
        return userRepository.logoutUser(user?.userToken ?: "")
    }

    fun getUserProfileImage(): Flow<Result<List<ImageResponseDTO>>>{
        return userRepository.getUserImageByName(userName)
    }
}