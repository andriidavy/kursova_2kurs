package com.example.registration.myISAM.ui.employee.customsProcessed

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
class MiEmployeeCustomsProcessedViewModel @Inject constructor(
    private val miEmployeeRepository: MiEmployeeRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _customProcessedArray = MutableStateFlow<List<CustomDTO>>(emptyList())
    val customProcessedArray: StateFlow<List<CustomDTO>>
        get() = _customProcessedArray
    private val employeeId = getUserId()

    init {
        getProcessedCustomsForEmployee()
    }

    private fun getProcessedCustomsForEmployee() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miEmployeeRepository.getProcessedCustomsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _customProcessedArray.value = it
                }
            }
        }
    }

        fun setCustomSent(customId: Int) {
            viewModelScope.launch(Dispatchers.IO) {
                miEmployeeRepository.setCustomSent(customId)
                getProcessedCustomsForEmployee()
            }
        }
    }