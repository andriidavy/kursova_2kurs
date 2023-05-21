package com.example.registration.viewmodel.employee.customsProcessed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.EmployeeRepository

class EmployeeCustomsProcessedViewModelFactory(
    private val employeeRepository: EmployeeRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeCustomsProcessedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeCustomsProcessedViewModel(employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}