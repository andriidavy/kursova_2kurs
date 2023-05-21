package com.example.registration.viewmodel.employee.reports.inWaiting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.EmployeeRepository
import com.example.registration.viewmodel.employee.reports.accepted.EmployeeReportsAcceptedViewModel

class EmployeeReportsInWaitingViewModelFactory  (
    private val employeeRepository: EmployeeRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeReportsInWaitingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeReportsInWaitingViewModel(employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}