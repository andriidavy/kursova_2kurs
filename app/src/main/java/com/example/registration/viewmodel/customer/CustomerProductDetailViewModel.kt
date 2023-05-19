package com.example.registration.viewmodel.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerProductDetailViewModel(private val customerRepository: CustomerRepository) :
    ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun addProductToCart(customerId: Int, productId: Int, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.addProductToCart(customerId, productId, quantity)
        }
        _message.value = "Додано до корзини"
    }
}