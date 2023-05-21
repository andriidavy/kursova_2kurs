package com.example.registration.viewmodel.employee.reports.rejected

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.EmployeeRepository

class EmployeeReportsRejectedViewModelFactory (
    private val employeeRepository: EmployeeRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeReportsRejectedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeReportsRejectedViewModel(employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}