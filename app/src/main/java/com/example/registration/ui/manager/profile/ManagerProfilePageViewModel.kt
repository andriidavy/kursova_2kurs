package com.example.registration.ui.manager.profile

import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.ManagerProfileDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
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
                result.collect {
                    _managerProfileDTO.value = it
                }
            }
        }
    }
}