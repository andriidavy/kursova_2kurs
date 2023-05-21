package com.example.registration.viewmodel.employee.reports.inWaiting

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.report.ReportDTO
import com.example.registration.repository.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeReportsInWaitingViewModel(private val employeeRepository: EmployeeRepository): ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _reportInWaitingArray = MutableLiveData<List<ReportDTO>>()
    val reportInWaitingArray: LiveData<List<ReportDTO>>
        get() = _reportInWaitingArray

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val employeeId: Int
        get() = sharedPreferences.getInt("employeeId", 0)

    fun getInWaitingReportsForEmployee(): LiveData<List<ReportDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = employeeRepository.getAllWaitingReportsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                _reportInWaitingArray.postValue(result)
            }
        }
        return reportInWaitingArray
    }
}