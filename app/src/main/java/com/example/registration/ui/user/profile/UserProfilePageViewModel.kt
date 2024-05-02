package com.example.registration.ui.user.profile

import androidx.lifecycle.ViewModel
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.model.users.data.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserProfilePageViewModel @Inject constructor(
    private val userRepository: UserRepository): ViewModel() {

    fun logout(user: UserDTO?): Flow<Result<Unit>>{
        return userRepository.logoutUser(user?.userToken ?: "")
    }
}