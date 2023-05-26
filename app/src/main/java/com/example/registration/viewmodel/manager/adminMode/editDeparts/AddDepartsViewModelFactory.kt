package com.example.registration.viewmodel.manager.adminMode.editDeparts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.ManagerRepository

class AddDepartsViewModelFactory (
    private val managerRepository: ManagerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDepartsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddDepartsViewModel(managerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}