package com.example.registration.viewmodel.customer_registration

import com.example.registration.repository.CustomerRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CustomerRegistrationViewModelFactory(private val repository: CustomerRepository) :ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CustomerRegistrationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CustomerRegistrationViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }