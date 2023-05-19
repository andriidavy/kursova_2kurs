package com.example.registration.viewmodel.customer.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.CustomerRepository
import com.example.registration.viewmodel.customer.CustomerMainPageViewModel

class CustomerProfilePageViewModelFactory (
    private val customerRepository: CustomerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerProfilePageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerProfilePageViewModel(customerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}