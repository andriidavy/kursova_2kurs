package com.example.registration.ui.manager.createdCustoms

import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManagerCreatedCustomsPageViewModel @Inject constructor(
    private val managerRepository: ManagerRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _customCreatedArray = MutableStateFlow<List<CustomDTO>>(emptyList())
    val customCreatedArray: StateFlow<List<CustomDTO>>
        get() = _customCreatedArray
    private val managerId = getUserId()

    init {
        getCreatedCustomsForManager()
    }

    private fun getCreatedCustomsForManager() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllCustomsWithoutEmployee(managerId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _customCreatedArray.value = it
                }
            }
        }
    }
}