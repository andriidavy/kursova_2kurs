package com.example.registration.datastore

import androidx.lifecycle.ViewModel
import com.example.registration.datastore.Constants.USER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class DataStoreViewModel @Inject constructor(
    private val datastoreRepository: DatastoreRepo
) : ViewModel() {

    fun storeUserId(value: Int) = runBlocking {
        datastoreRepository.putInt(USER_ID, value)
    }

    fun getUserId(): Int = runBlocking {
        datastoreRepository.getInt(USER_ID)!!
    }

    fun clearPreferences(key: String) = runBlocking {
        datastoreRepository.clearPreferences(key)
    }

}