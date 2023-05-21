package com.example.registration.viewmodel.manager.reportsInWaiting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.ManagerRepository

class ManagerReportsInWaitingDetailViewModelFactory   ( private val managerRepository: ManagerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManagerReportsInWaitingDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ManagerReportsInWaitingDetailViewModel(managerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}