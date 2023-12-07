package com.example.registration.myISAM.ui.employee.reportsInWaiting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.registration.database.myIsam.employee.MiEmployeeRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.report.ReportDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiEmployeeReportsInWaitingViewModel @Inject constructor(
    private val miEmployeeRepository: MiEmployeeRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val _reportInWaitingArray = MutableLiveData<List<ReportDTO>>()
    val reportInWaitingArray: LiveData<List<ReportDTO>>
        get() = _reportInWaitingArray
    private val employeeId = getUserId()

    init {
        getInWaitingReportsForEmployee()
    }

    private fun getInWaitingReportsForEmployee() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miEmployeeRepository.getAllWaitingReportsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _reportInWaitingArray.value = it
                }
            }
        }
    }
}