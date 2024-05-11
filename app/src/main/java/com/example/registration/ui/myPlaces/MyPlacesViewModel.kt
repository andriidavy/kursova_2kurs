package com.example.registration.ui.myPlaces

import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MyPlacesViewModel @Inject constructor(
    private val userRepository: UserRepository,
    datastoreRepository: DatastoreRepo
) :
    DataStoreViewModel(datastoreRepository) {

}