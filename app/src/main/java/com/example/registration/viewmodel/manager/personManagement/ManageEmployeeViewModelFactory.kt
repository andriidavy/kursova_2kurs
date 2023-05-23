package com.example.registration.viewmodel.manager.personManagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.ManagerRepository

class ManageEmployeeViewModelFactory (private val managerRepository: ManagerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManageEmployeeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ManageEmployeeViewModel(managerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}