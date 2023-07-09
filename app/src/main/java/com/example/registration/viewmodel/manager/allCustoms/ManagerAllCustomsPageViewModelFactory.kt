package com.example.registration.viewmodel.manager.allCustoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.manager.ManagerRepository

class ManagerAllCustomsPageViewModelFactory(
    private val managerRepository: ManagerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManagerAllCustomsPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ManagerAllCustomsPageViewModel(managerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}