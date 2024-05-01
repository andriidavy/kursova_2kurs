package com.example.registration.ui.customer.profile

import androidx.lifecycle.ViewModel
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.ui.login.data.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CustomerProfilePageViewModel @Inject constructor(
    private val userRepository: UserRepository): ViewModel() {

    fun logout(user: UserDTO?): Flow<Result<Unit>>{
        return userRepository.logoutUser(user?.userToken ?: "")
    }
}