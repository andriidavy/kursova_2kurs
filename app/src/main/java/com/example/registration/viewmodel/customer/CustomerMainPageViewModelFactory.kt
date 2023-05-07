package com.example.registration.viewmodel.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.CustomerRepository
import com.example.registration.repository.ProductRepository



class CustomerMainPageViewModelFactory(
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomerMainPageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CustomerMainPageViewModel(customerRepository, productRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}