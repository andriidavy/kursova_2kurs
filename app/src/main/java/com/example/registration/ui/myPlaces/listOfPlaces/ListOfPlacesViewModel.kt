package com.example.registration.ui.myPlaces.listOfPlaces

import com.example.registration.database.backendless.location.LocationRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.places.PlaceItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ListOfPlacesViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(
    datastoreRepository
) {
    private val userToken: String = getUser()?.userToken ?: ""
    private val userName: String = getUser()?.name ?: ""
    fun getAllPlaces(): Flow<Result<List<PlaceItem>>> {
        return locationRepository.getAllPlaces()
    }

    fun getPlacesByDescription(searchLine: String): Flow<Result<List<PlaceItem>>>{
        return locationRepository.getPlacesByDescription(searchLine)
    }


    fun getPlacesByTag(searchLine: String): Flow<Result<List<PlaceItem>>>{
        return locationRepository.getPlacesByTag(searchLine)
    }
    fun deletePlace(placeId: String): Flow<Result<Unit>> {
        return locationRepository.deletePlace(userToken, placeId)
    }
    fun getPlacesByDistance(searchLine: String): Flow<Result<List<PlaceItem>>>{
        return locationRepository.getPlacesByDistance(userName, searchLine)
    }
}