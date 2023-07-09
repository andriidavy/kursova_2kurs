package com.example.registration.viewmodel.employee.reports.rejected

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.report.ReportDTO
import com.example.registration.database.employee.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeReportsRejectedViewModel(val employeeRepository: EmployeeRepository): ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _reportRejectedArray = MutableLiveData<List<ReportDTO>>()
    val reportRejectedArray: LiveData<List<ReportDTO>>
        get() = _reportRejectedArray

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val employeeId: Int
        get() = sharedPreferences.getInt("employeeId", 0)

    fun getRejectedReportsForEmployee(): LiveData<List<ReportDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = employeeRepository.getAllRejectedReportsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                _reportRejectedArray.postValue(result)
            }
        }
        return reportRejectedArray
    }
}