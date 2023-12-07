package com.example.registration.myISAM.ui.manager.createdCustoms

import androidx.lifecycle.viewModelScope
import com.example.registration.database.myIsam.manager.MiManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.custom.CustomDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiManagerCreatedCustomsPageViewModel @Inject constructor(
    private val miManagerRepository: MiManagerRepository,
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
            val result = miManagerRepository.getAllCustomsWithoutEmployee(managerId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _customCreatedArray.value = it
                }
            }
        }
    }
}