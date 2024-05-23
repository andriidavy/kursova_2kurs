package com.example.registration.database.backendless.friends

import android.util.Log
import com.example.registration.model.friends.FriendItem
import com.example.registration.model.users.data.LocationDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FriendsRepository @Inject constructor(private val friendsApi: FriendsApi) {
    fun getFriendsList(myUserId: String): Flow<Result<List<FriendItem>>> = flow {
        try {
            val whereClause = "inviterId = '$myUserId' AND status ='ACCEPTED'"
            val friendsList = friendsApi.getAcceptedFriendsList(whereClause)

            val invitedIds = friendsList.map { it.invitedId }
            val whereClauseForInfo = "objectId IN (${invitedIds.joinToString(",") { "'$it'" }})"
            val friendsInfoList = friendsApi.getAcceptedFriendsListInfo(whereClauseForInfo)

            val friendsInfoMap = friendsInfoList.associateBy { it.objectId }

            val updatedFriendsList = friendsList.map { friendItem ->
                val friendInfo = friendsInfoMap[friendItem.invitedId]
                friendItem.apply {
                    name = friendInfo?.name ?: ""
                    email = friendInfo?.email ?: ""
                    location = friendInfo?.location ?: LocationDTO("Point", listOf(0.0, 0.0))
                }
            }
            Log.e("getFriends", "friendsList: $updatedFriendsList")
            emit(Result.success(updatedFriendsList))
        } catch (e: Exception) {
            Log.e("getFriends", "failed")
            emit(Result.failure(e))
        }
    }
}

