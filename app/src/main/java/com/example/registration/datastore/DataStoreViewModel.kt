package com.example.registration.datastore

import androidx.lifecycle.ViewModel
import com.example.registration.datastore.Constants.USER_EMAIL
import com.example.registration.datastore.Constants.USER_ID
import com.example.registration.datastore.Constants.USER_TOKEN
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
open class DataStoreViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepo
) : ViewModel() {

    fun storeUserToken(value: String) = runBlocking {
        datastoreRepository.putString(USER_TOKEN, value)
    }

    fun getUserToken(): String = runBlocking {
        datastoreRepository.getString(USER_TOKEN)!!
    }

    fun storeUserObjectId(value: String) = runBlocking {
        datastoreRepository.putString(USER_EMAIL, value)
    }

    fun getUserObjectId() = runBlocking {
        datastoreRepository.getString(USER_EMAIL)!!
    }

    fun clearPreferences(key: String) = runBlocking {
        datastoreRepository.clearPreferences(key)
    }


    fun getUserId(): Int = runBlocking {
        datastoreRepository.getInt(USER_ID)!!
    }

}