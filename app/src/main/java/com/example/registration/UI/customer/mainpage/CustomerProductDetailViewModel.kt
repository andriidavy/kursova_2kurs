package com.example.registration.UI.customer.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.customer.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CustomerProductDetailViewModel @Inject constructor(
    private val customerRepository: CustomerRepository):
    ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun addProductToCart(customerId: Int, productId: Int, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.addProductToCart(customerId, productId, quantity)
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    _message.value = "Додано до корзини"
                }.onFailure { exception ->
                    _message.value = "Помилка: ${exception.message}"
                }
            }
        }
    }
}