package com.example.registration.ui.myFriends.inviteFriend

import com.example.registration.database.backendless.friends.FriendsRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.friends.AddingFriendDTO
import com.example.registration.model.friends.SearchFriendItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class InviteFriendViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userToken: String = getUser()?.userToken ?: ""
    private val userId: String = getUser()?.objectId ?: ""

    fun getFriendByName(searchLine: String, argUserId: String): Flow<Result<List<SearchFriendItem>>> {
        return friendsRepository.getFriendByName(searchLine, argUserId)
    }

    fun addingFriend(invitedId: String): Flow<Result<AddingFriendDTO>> {
        val friendDTO = AddingFriendDTO(userId, invitedId, "INVITE")
        return friendsRepository.addFriend(userToken, friendDTO)
    }
}