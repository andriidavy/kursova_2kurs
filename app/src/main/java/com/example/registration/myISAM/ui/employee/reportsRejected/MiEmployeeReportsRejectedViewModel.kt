package com.example.registration.myISAM.ui.employee.reportsRejected

import androidx.lifecycle.viewModelScope
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.myIsam.employee.MiEmployeeRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.report.ReportDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiEmployeeReportsRejectedViewModel @Inject constructor(
    val miEmployeeRepository: MiEmployeeRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val _reportRejectedArray = MutableStateFlow<List<ReportDTO>>(emptyList())
    val reportRejectedArray: StateFlow<List<ReportDTO>>
        get() = _reportRejectedArray
    private val employeeId = getUserId()

    init {
        getRejectedReportsForEmployee()
    }

    private fun getRejectedReportsForEmployee() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miEmployeeRepository.getAllRejectedReportsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _reportRejectedArray.value = it
                }
            }
        }
    }
}