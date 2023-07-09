package com.example.registration.viewmodel.employee.reports.accepted

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.employee.EmployeeRepository

class EmployeeReportsAcceptedViewModelFactory (
    private val employeeRepository: EmployeeRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeReportsAcceptedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeReportsAcceptedViewModel(employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}