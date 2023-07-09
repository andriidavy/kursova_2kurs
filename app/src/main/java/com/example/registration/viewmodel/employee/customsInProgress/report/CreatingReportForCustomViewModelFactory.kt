package com.example.registration.viewmodel.employee.customsInProgress.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.employee.EmployeeRepository

class CreatingReportForCustomViewModelFactory (
    private val employeeRepository: EmployeeRepository
    ) :
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CreatingReportForCustomViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CreatingReportForCustomViewModel(employeeRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}