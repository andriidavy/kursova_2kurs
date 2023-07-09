package com.example.registration.viewmodel.employee.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.employee.EmployeeRepository

class EmployeeProfilePageViewModelFactory(private val employeeRepository: EmployeeRepository
) :
ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeProfilePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeProfilePageViewModel(employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}