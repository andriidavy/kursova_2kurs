package com.example.registration.viewmodel.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.CustomerRepository


class CustomerMainPageViewModelFactory(
    private val customerRepository: CustomerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerMainPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerMainPageViewModel(customerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}