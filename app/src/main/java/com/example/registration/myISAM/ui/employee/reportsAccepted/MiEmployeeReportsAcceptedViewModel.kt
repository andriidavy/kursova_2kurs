package com.example.registration.myISAM.ui.employee.reportsAccepted

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.registration.database.employee.EmployeeRepository
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
class MiEmployeeReportsAcceptedViewModel @Inject constructor(
    private val miEmployeeRepository: MiEmployeeRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _reportAcceptedArray = MutableLiveData<List<ReportDTO>>()
    val reportAcceptedArray: LiveData<List<ReportDTO>>
        get() = _reportAcceptedArray
    private val employeeId = getUserId()

    init {
        getAcceptedReportsForEmployee()
    }

    private fun getAcceptedReportsForEmployee() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miEmployeeRepository.getAllAcceptedReportsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _reportAcceptedArray.value = it
                }
            }
        }
    }
}