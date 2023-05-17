package com.example.registration.viewmodel.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.CustomerRepository

class CustomerProductDetailViewModelFactory(
    private val customerRepository: CustomerRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerProductDetailViewModel(customerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}