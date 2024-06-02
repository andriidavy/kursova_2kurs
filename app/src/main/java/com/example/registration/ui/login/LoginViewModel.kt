package com.example.registration.ui.login

import androidx.lifecycle.viewModelScope
import com.example.registration.database.backendless.notification.NotificationRepository
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.device.DeviceRegistrationResponse
import com.example.registration.model.users.data.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""

    fun login(email: String, password: String): Flow<Result<UserDTO>> {
        return userRepository.loginUser(email, password)
    }

    fun restorePassword(email: String) {
        viewModelScope.launch { userRepository.restorePassword(email) }
    }

    fun registerDevice(
        deviceToken: String,
        deviceId: String,
        os: String,
        osVersion: String,
        channels: List<String>?,
        expiration: Long?
    ): Flow<Result<DeviceRegistrationResponse>> {
        return notificationRepository.registerDevice(
            deviceToken,
            deviceId,
            os,
            osVersion,
            userToken,
            channels,
            expiration
        )
    }
}

