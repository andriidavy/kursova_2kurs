package com.example.registration.viewmodel.manager.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.ManagerRepository


class ManagerProfilePageViewModelFactory  (private val managerRepository: ManagerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ManagerProfilePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ManagerProfilePageViewModel(managerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}