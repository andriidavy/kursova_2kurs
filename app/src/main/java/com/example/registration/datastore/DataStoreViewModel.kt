package com.example.registration.datastore

import androidx.lifecycle.ViewModel
import com.example.registration.datastore.Constants.USER_ID
import com.example.registration.datastore.Constants.USER_KEY
import com.example.registration.model.users.data.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class DataStoreViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepo
) : ViewModel() {

    fun storeUser(user: UserDTO) = runBlocking {
        datastoreRepository.putUser(USER_KEY, user)
    }

    fun getUser(): UserDTO? = runBlocking {
        datastoreRepository.getUser(USER_KEY)
    }

    fun clearPreferences(key: String) = runBlocking {
        datastoreRepository.clearPreferences(key)
    }


    fun getUserId(): Int = runBlocking {
        datastoreRepository.getInt(USER_ID)!!
    }

}