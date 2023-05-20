package com.example.registration.viewmodel.manager.createdCustoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.ManagerRepository

class EmployeesListForAssigneeToCustomViewModelFactory(
    private val managerRepository: ManagerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeesListForAssigneeToCustomViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeesListForAssigneeToCustomViewModel(managerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}