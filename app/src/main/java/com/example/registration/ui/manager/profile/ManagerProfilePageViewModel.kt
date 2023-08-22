package com.example.registration.ui.manager.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.ManagerProfileDTO
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ManagerProfilePageViewModel @Inject constructor(
    private val managerRepository: ManagerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _managerProfileDTO = MutableStateFlow(ManagerProfileDTO())
    val managerProfileDTO: StateFlow<ManagerProfileDTO>
        get() = _managerProfileDTO
    private val managerId = getUserId()

    init {
        getManagerProfileById()
    }

    private fun getManagerProfileById() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getManagerProfile(managerId)
            withContext(Dispatchers.Main) {
                _managerProfileDTO.value = result
            }
        }
    }
}