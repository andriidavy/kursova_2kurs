package com.example.registration.viewmodel.customer.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.CustomerRepository

class CustomerCartItemViewModelFactory (
    private val customerRepository: CustomerRepository
    ) :
    ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CustomerCartItemViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CustomerCartItemViewModel(customerRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}