package com.example.registration.viewmodel.manager.adminMode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.ManagerRepository

class ManagerDepartDetailViewModelFactory (
    private val managerRepository: ManagerRepository
    ) :
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ManagerDepartDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ManagerDepartDetailViewModel(managerRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}