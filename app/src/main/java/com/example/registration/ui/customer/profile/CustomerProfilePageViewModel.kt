package com.example.registration.ui.customer.profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.ui.login.data.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerProfilePageViewModel @Inject constructor(
    private val userRepository: UserRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val userToken = getUserToken()
    private val userObjectId = getUserObjectId()

    init {
        getUserProfile()
        Log.d("UserRepository", "user token: ${getUserToken()}")
        Log.d("UserRepository", "user id: ${getUserObjectId()}")
        viewModelScope.launch {
            Log.d(
                "UserRepository",
                "is token valid: ${userRepository.isTokenValid(getUserToken())}"
            )
        }
    }

    fun getUserProfile(): Flow<Result<UserDTO>> {
        return userRepository.getUser(userObjectId, userToken)
    }
}