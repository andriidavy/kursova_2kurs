package com.example.registration.ui.user.mainpage

import android.location.Location
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.data.LocationDTO
import com.example.registration.model.users.data.UserLocationDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UserMainPageViewModel @Inject constructor(
    private val userRepository: UserRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""
    private val userId: String = getUser()?.objectId ?: ""

    fun updateUserLocation(location: Location): Flow<Result<UserLocationDTO>> {
        val userLocation =
            UserLocationDTO(
                userToken,
                userId,
                LocationDTO(coordinates = listOf(location.longitude, location.latitude))
            )
        return userRepository.updateUserLocation(userToken, userLocation)
    }
}