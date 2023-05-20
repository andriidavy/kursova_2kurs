package com.example.registration.viewmodel.manager.createdCustoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.CustomerRepository
import com.example.registration.repository.ManagerRepository
import com.example.registration.viewmodel.customer.CustomerMainPageViewModel

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