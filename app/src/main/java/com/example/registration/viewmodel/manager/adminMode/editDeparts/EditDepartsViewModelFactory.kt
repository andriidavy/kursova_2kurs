package com.example.registration.viewmodel.manager.adminMode.editDeparts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.ManagerRepository

class EditDepartsViewModelFactory (
    private val managerRepository: ManagerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditDepartsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditDepartsViewModel(managerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}