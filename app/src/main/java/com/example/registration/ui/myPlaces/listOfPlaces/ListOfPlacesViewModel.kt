package com.example.registration.ui.myPlaces.listOfPlaces

import com.example.registration.database.backendless.location.LocationRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.places.PlaceItem
import com.example.registration.model.places.likes.AddLikeForPlaceData
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
    private val userId: String = getUser()?.objectId ?: ""
    fun getAllPlaces(): Flow<Result<List<PlaceItem>>> {
        return locationRepository.getAllPlaces(userId)
    }

    fun getPlacesByDescription(searchLine: String): Flow<Result<List<PlaceItem>>>{
        return locationRepository.getPlacesByDescription(searchLine)
    }
    fun addLikeToPlace(placeId: String): Flow<Result<AddLikeForPlaceData>>{
        return locationRepository.addLikeToPlace(userToken, userId, placeId)
    }
    fun deleteLikeForPlace(placeId: String): Flow<Result<Unit>> {
        return locationRepository.deleteLikeForPlace(userToken, placeId)
    }
    fun getPlacesByTag(searchLine: String): Flow<Result<List<PlaceItem>>>{
        return locationRepository.getPlacesByTag(searchLine)
    }
    fun getPlacesByDistance(searchLine: String): Flow<Result<List<PlaceItem>>>{
        return locationRepository.getPlacesByDistance(userName, searchLine)
    }
    fun deletePlace(placeId: String): Flow<Result<Unit>> {
        return locationRepository.deletePlace(userToken, placeId)
    }
}