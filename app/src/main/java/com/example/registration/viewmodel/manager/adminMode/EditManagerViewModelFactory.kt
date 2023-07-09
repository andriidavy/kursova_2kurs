package com.example.registration.viewmodel.manager.adminMode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.manager.ManagerRepository

class EditManagerViewModelFactory (
    private val managerRepository: ManagerRepository
    ) :
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EditManagerViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EditManagerViewModel(managerRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}