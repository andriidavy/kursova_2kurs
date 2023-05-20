package com.example.registration.viewmodel.employee.customsInProgress.report

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.repository.EmployeeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreatingReportForCustomViewModel(private val employeeRepository: EmployeeRepository) :
    ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val employeeId: Int
        get() = sharedPreferences.getInt("employeeId", 0)

    fun createReport(
        customId: Int,
        reportText: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            employeeRepository.createReport(employeeId, customId, reportText)
        }
    }
}