package com.example.registration.myISAM.ui.employee.customsInProgress

import androidx.lifecycle.viewModelScope
import com.example.registration.database.myIsam.employee.MiEmployeeRepository
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
class MiEmployeeCustomsInProgressViewModel @Inject constructor(
    private val miEmployeeRepository: MiEmployeeRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _customInProgressArray = MutableStateFlow<List<CustomDTO>>(emptyList())
    val customInProgressArray: StateFlow<List<CustomDTO>>
        get() = _customInProgressArray
    private val employeeId = getUserId()

    init {
        getInProgressCustomsForEmployee()
    }

    private fun getInProgressCustomsForEmployee() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miEmployeeRepository.getProcessingCustomsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _customInProgressArray.value = it
                }
            }
        }
    }
}