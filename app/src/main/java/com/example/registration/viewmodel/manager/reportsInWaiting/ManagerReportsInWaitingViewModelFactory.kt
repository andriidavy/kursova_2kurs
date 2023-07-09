package com.example.registration.viewmodel.manager.reportsInWaiting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.manager.ManagerRepository

class ManagerReportsInWaitingViewModelFactory  ( private val managerRepository: ManagerRepository
) :
ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManagerReportsInWaitingViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ManagerReportsInWaitingViewModel(managerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}