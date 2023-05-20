package com.example.registration.viewmodel.employee.customsInProgress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.EmployeeRepository


class EmployeeCustomsInProgressViewModelFactory(
    private val employeeRepository: EmployeeRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeCustomsInProgressViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeCustomsInProgressViewModel(employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}