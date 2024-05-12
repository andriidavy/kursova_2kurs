package com.example.registration.database.backendless.location

import android.util.Log
import com.example.registration.model.places.AddingPlaceDTO
import com.example.registration.model.places.PlaceItem
import com.example.registration.model.users.data.UserLocationDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.sql.Timestamp
import javax.inject.Inject

class LocationRepository @Inject constructor(private val locationApi: LocationApi) {
    fun addPlace(userToken: String, addingPlaceDTO: AddingPlaceDTO): Flow<Result<AddingPlaceDTO>> =
        flow {
            try {
                val addedPlace = locationApi.addPlace(userToken, addingPlaceDTO)
                Log.e("updateUser", "user: $addedPlace")
                emit(Result.success(addedPlace))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }

    fun deletePlace(userToken: String, placeId: String): Flow<Result<Unit>> =
        flow {
            try {
                locationApi.deletePlace(userToken, placeId)
                Log.e("deletePlace", "place is deleted")
                emit(Result.success(Unit))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }

    fun getMyLocation(userName: String): Flow<Result<List<UserLocationDTO>>> = flow {
        try {
            val whereClause = "name = '$userName'"
            val getMyLocation = locationApi.getMyLocation(whereClause)
            Log.e("getUser", "user: $getMyLocation")
            emit(Result.success(getMyLocation))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getAllPlaces(): Flow<Result<List<PlaceItem>>> = flow {
        try {
            val getMyLocation = locationApi.getAllPlaces()
            Log.e("getLocations", "locations: $getMyLocation")
            emit(Result.success(getMyLocation))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}