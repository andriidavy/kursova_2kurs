package com.example.registration.ui.myFriends.inviteToMe

import com.example.registration.database.backendless.friends.FriendsRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.friends.AcceptFriendDTO
import com.example.registration.model.friends.FriendItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class InviteToMeViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userId: String = getUser()?.objectId ?: ""
    private val userToken: String = getUser()?.userToken ?: ""

    fun getInviteToMe(): Flow<Result<List<FriendItem>>> {
        return friendsRepository.getInviteToMeList(userId)
    }

    fun acceptInvite(friendId: String): Flow<Result<AcceptFriendDTO>> {
        val acceptFriendDTO = AcceptFriendDTO(friendId, "ACCEPTED")
        return friendsRepository.acceptInvite(userToken, acceptFriendDTO)
    }

    fun rejectInvite(friendId: String): Flow<Result<Unit>>{
        return friendsRepository.deleteFriend(userToken, friendId)
    }
}