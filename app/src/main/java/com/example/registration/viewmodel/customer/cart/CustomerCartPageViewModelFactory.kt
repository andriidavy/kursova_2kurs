package com.example.registration.viewmodel.customer.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.customer.CustomerRepository

class CustomerCartPageViewModelFactory(
    private val customerRepository: CustomerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerCartPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerCartPageViewModel(customerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}