package com.example.registration.ui.myPlaces.addPlace

import com.example.registration.database.backendless.location.LocationRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.places.AddingPlaceDTO
import com.example.registration.model.users.data.LocationDTO
import com.example.registration.model.users.data.UserLocationDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddPlaceViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""
    private val userName: String = getUser()?.name ?: ""

    fun addPlace(description: String, place: LocationDTO): Flow<Result<AddingPlaceDTO>> {
        val addingPlaceDTO = AddingPlaceDTO(description, place, userName)
        return locationRepository.addPlace(userToken, addingPlaceDTO)
    }

    fun getMyLocation(): Flow<Result<List<UserLocationDTO>>> {
        return locationRepository.getMyLocation(userName)
    }
}