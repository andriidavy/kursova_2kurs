package com.example.registration.ui.login

import androidx.lifecycle.viewModelScope
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.data.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    fun login(email: String, password: String): Flow<Result<UserDTO>> {
        return userRepository.loginUser(email, password)
    }

    fun restorePassword(email: String) {
        viewModelScope.launch { userRepository.restorePassword(email) }
    }
}

