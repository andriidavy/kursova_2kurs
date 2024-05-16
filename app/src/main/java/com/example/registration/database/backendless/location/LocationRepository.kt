package com.example.registration.database.backendless.location

import android.util.Log
import com.example.registration.model.places.AddingPlaceDTO
import com.example.registration.model.places.PlaceItem
import com.example.registration.model.users.data.UserLocationDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

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

    fun getPlacesByDescription(searchLine: String): Flow<Result<List<PlaceItem>>> = flow {
        try {
            val whereClause = "description %20LIKE%20 '$searchLine'"
            val getMyLocation = locationApi.getPlacesBySearchLine(whereClause)
            Log.e("getLocations", "locations: $getMyLocation")
            emit(Result.success(getMyLocation))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getPlacesByTag(searchLine: String): Flow<Result<List<PlaceItem>>> = flow {
        try {
            val whereClause = "tags %20LIKE%20 '$searchLine'"
            val getMyLocation = locationApi.getPlacesBySearchLine(whereClause)
            Log.e("getLocations", "locations: $getMyLocation")
            emit(Result.success(getMyLocation))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun getPlacesByDistance(userName: String, searchLine: String): Flow<Result<List<PlaceItem>>> =
        flow {
            try {
                val whereClause = "name = '$userName'"
                val requiredDistance = searchLine.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Invalid distance value")
                val myLocation = locationApi.getMyLocation(whereClause).firstOrNull()
                    ?: throw IllegalStateException("Unable to retrieve user location")

                val myLatitude = myLocation.location.coordinates[1]
                val myLongitude = myLocation.location.coordinates[0]
                val allPlaces = locationApi.getAllPlaces()

                val selectedPlaces = allPlaces
                    .filter { place ->
                        val placeLatitude = place.location.coordinates[1]
                        val placeLongitude = place.location.coordinates[0]
                        val distanceBetween = haversineDistance(
                            myLatitude,
                            myLongitude,
                            placeLatitude,
                            placeLongitude
                        )
                        distanceBetween < requiredDistance
                    }

                selectedPlaces.map { place ->
                    val placeLatitude = place.location.coordinates[1]
                    val placeLongitude = place.location.coordinates[0]
                    val distanceBetween =
                        haversineDistance(myLatitude, myLongitude, placeLatitude, placeLongitude)
                    place.distanceToMe = roundToDecimals(distanceBetween, 2)

                }

                Log.d("getLocations", "locations: $selectedPlaces")
                emit(Result.success(selectedPlaces))
            } catch (e: Exception) {
                Log.e("getPlacesByDistance", "Error: ${e.message}", e)
                emit(Result.failure(e))
            }
        }

    private fun roundToDecimals(number: Double, numDecimalPlaces: Int): Double {
        val factor = 10.0.pow(numDecimalPlaces)
        return kotlin.math.round(number * factor) / factor
    }

    private fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Радиус Земли в километрах

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a =
            sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(
                2
            )
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c
    }
}