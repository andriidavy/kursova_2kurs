package com.example.registration.database.backendless.friends

import android.util.Log
import com.example.registration.database.backendless.location.LocationApi
import com.example.registration.global.LocationObj.haversineDistance
import com.example.registration.global.LocationObj.roundToDecimals
import com.example.registration.model.friends.FriendItem
import com.example.registration.model.friends.SearchFriendItem
import com.example.registration.model.users.data.GuestUserDTO
import com.example.registration.model.users.data.LocationDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FriendsRepository @Inject constructor(
    private val friendsApi: FriendsApi,
    private val locationApi: LocationApi
) {
    fun getFriendsList(
        myUserId: String,
        searchDistance: String
    ): Flow<Result<List<FriendItem>>> = flow {
        try {
            // Step 1: Fetch the list of accepted friends
            val getFriendsWhereClause =
                "(inviterId = '$myUserId' OR invitedId = '$myUserId') AND status = 'ACCEPTED'"
            val friendsList = friendsApi.getAcceptedFriendsList(getFriendsWhereClause)
            Log.d("getFriendsList", "Fetched friends list: $friendsList")

            // Step 2: Fetch user's location
            val getMyLocationWhereClause = "objectId = '$myUserId'"
            val myLocation = locationApi.getMyLocation(getMyLocationWhereClause).firstOrNull()
                ?: throw IllegalStateException("Unable to retrieve user location")
            val myLatitude = myLocation.location.coordinates[1]
            val myLongitude = myLocation.location.coordinates[0]
            Log.d("getFriendsList", "User location: ($myLatitude, $myLongitude)")

            // Step 3: Fetch friends' detailed info
            val friendIds = friendsList.map {
                if (it.inviterId == myUserId) it.invitedId else it.inviterId
            }
            val whereClauseForInfo = "objectId IN (${friendIds.joinToString(",") { "'$it'" }})"
            val friendsInfoList = friendsApi.getAcceptedFriendsListInfo(whereClauseForInfo)
            val friendsInfoMap = friendsInfoList.associateBy { it.objectId }
            Log.d("getFriendsList", "Friends detailed info: $friendsInfoList")

            // Step 4: Update friends list with detailed info
            val updatedFriendsList = friendsList.map { friendItem ->
                val friendId =
                    if (friendItem.inviterId == myUserId) friendItem.invitedId else friendItem.inviterId
                val friendInfo = friendsInfoMap[friendId]
                friendItem.apply {
                    name = friendInfo?.name ?: ""
                    email = friendInfo?.email ?: ""
                    location = friendInfo?.location ?: LocationDTO("Point", listOf(0.0, 0.0))
                }
            }

            // Step 5: If distance filter is applied, filter the friends list
            if (searchDistance.isNotBlank()) {
                val requiredDistance = searchDistance.toDoubleOrNull()
                    ?: throw IllegalArgumentException("Invalid distance value")
                val selectedPlaces = updatedFriendsList.filter { place ->
                    val placeLatitude = place.location.coordinates[1]
                    val placeLongitude = place.location.coordinates[0]
                    val distanceBetween = haversineDistance(
                        myLatitude,
                        myLongitude,
                        placeLatitude,
                        placeLongitude
                    )
                    distanceBetween <= requiredDistance
                }

                // Step 6: Update the distance to each selected place
                selectedPlaces.forEach { place ->
                    val placeLatitude = place.location.coordinates[1]
                    val placeLongitude = place.location.coordinates[0]
                    val distanceBetween = haversineDistance(
                        myLatitude,
                        myLongitude,
                        placeLatitude,
                        placeLongitude
                    )
                    place.distanceToMe = roundToDecimals(distanceBetween, 2)
                }
                Log.d("getFriendsList", "Filtered friends list: $selectedPlaces")
                emit(Result.success(selectedPlaces))
            } else {
                updatedFriendsList.forEach { place ->
                    val placeLatitude = place.location.coordinates[1]
                    val placeLongitude = place.location.coordinates[0]
                    val distanceBetween = haversineDistance(
                        myLatitude,
                        myLongitude,
                        placeLatitude,
                        placeLongitude
                    )
                    place.distanceToMe = roundToDecimals(distanceBetween, 2)
                }
                emit(Result.success(updatedFriendsList))
            }
        } catch (e: Exception) {
            Log.e("getFriendsList", "Failed to get friends list", e)
            emit(Result.failure(e))
        }
    }

    fun getFriendByName(userName: String): Flow<Result<List<SearchFriendItem>>> = flow {
        try {
            val whereClause = "name = '$userName'"
            val getUser = friendsApi.getFriendByName(whereClause)
            Log.e("getUser", "user: $getUser")
            emit(Result.success(getUser))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun deleteFriend(userToken: String, friendsId: String): Flow<Result<Unit>> =
        flow {
            try {
                friendsApi.deleteFriend(userToken, friendsId)
                Log.e("deleteFriend", "friend is deleted")
                emit(Result.success(Unit))
            } catch (e: Exception) {
                emit(Result.failure(e))
            }
        }
}

