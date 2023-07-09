package com.example.registration.viewmodel.customer.custom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.customer.CustomerRepository

class CustomerCustomPageViewModelFactory (
    private val customerRepository: CustomerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerCustomPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerCustomPageViewModel(customerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}