package com.example.registration.viewmodel.manager.createdCustoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.manager.ManagerRepository

class ManagerCreatedCustomsPageViewModelFactory (
    private val managerRepository: ManagerRepository
    ) :
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ManagerCreatedCustomsPageViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ManagerCreatedCustomsPageViewModel(managerRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}