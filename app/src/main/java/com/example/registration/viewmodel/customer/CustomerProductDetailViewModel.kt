package com.example.registration.viewmodel.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerProductDetailViewModel(private val customerRepository: CustomerRepository) :
    ViewModel() {

    fun addProductToCart(customerId: Int, productId: Int, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.addProductToCart(customerId, productId, quantity)
        }
    }
}