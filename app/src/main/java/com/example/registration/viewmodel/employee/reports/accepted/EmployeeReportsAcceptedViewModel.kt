package com.example.registration.viewmodel.employee.reports.accepted

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.repository.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeeReportsAcceptedViewModel(private val employeeRepository: EmployeeRepository): ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _reportAcceptedArray = MutableLiveData<List<ReportDTO>>()
    val reportAcceptedArray: LiveData<List<ReportDTO>>
        get() = _reportAcceptedArray

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val employeeId: Int
        get() = sharedPreferences.getInt("employeeId", 0)

    fun getAcceptedReportsForEmployee(): LiveData<List<ReportDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = employeeRepository.getAllAcceptedReportsForEmployee(employeeId)
            withContext(Dispatchers.Main) {
                _reportAcceptedArray.postValue(result)
            }
        }
        return reportAcceptedArray
    }
}