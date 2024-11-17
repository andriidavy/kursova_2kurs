package com.example.registration.ui.manager.adminMode.staff

import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.StaffDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StaffViewModel @Inject constructor(
    private val managerRepository: ManagerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val token = "Bearer ${getUserToken()}"

    private val _staffArray = MutableStateFlow<List<StaffDTO>>(emptyList())
    val staffArray: StateFlow<List<StaffDTO>>
        get() = _staffArray

    init {
        getStaff()
    }

    private fun getStaff() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getStaff(token)
            withContext(Dispatchers.Main) {
                result.collect {
                    _staffArray.value = it
                }
            }
        }
    }
}