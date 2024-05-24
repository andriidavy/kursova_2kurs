package com.example.registration.ui.myFriends

import com.example.registration.database.backendless.friends.FriendsRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.friends.FriendItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MyFriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val userId: String = getUser()?.objectId ?: ""

    fun getFriendsList(searchDistance: String): Flow<Result<List<FriendItem>>> {
        return friendsRepository.getFriendsList(userId, searchDistance)
    }
}