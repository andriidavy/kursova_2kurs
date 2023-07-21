package com.example.registration.ui.customer.cart

import android.content.SharedPreferences
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.registration.R
import com.example.registration.model.cart.CartProductDTO
import com.example.registration.database.customer.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerCartPageViewModel @Inject constructor(private val customerRepository: CustomerRepository) :
    ViewModel() {

    private val _cartProductsArrayDTO = MutableLiveData<List<CartProductDTO>>()
    val cartProductsArrayDTO: LiveData<List<CartProductDTO>>
        get() = _cartProductsArrayDTO

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message


    fun getAllCartProducts(customerId: Int): LiveData<List<CartProductDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCartProducts(customerId)
            withContext(Dispatchers.Main) {
                _cartProductsArrayDTO.postValue(result)
            }
        }
        return cartProductsArrayDTO
    }

    fun createCustom(customerId: Int): LiveData<Int> {
        val customId = MutableLiveData<Int>()

        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.createCustom(customerId)

            withContext(Dispatchers.Main) {
                result.onSuccess { orderId ->
                    customId.value = orderId
                    _message.value = "Замовлення створено!"
                }.onFailure {
                    _message.value = "Помилка обробки замовлення!"
                }
            }
        }
        return customId
    }

    fun removeProductFromCart(customerId: Int, productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.removeProductFromCart(customerId, productId)
            getAllCartProducts()
        }
        _message.value = "Товар видалено з корзини"
    }

    fun clearCart(customerId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.clearCart(customerId)
            getAllCartProducts()
        }
        _message.value = "Корзину очищено"
    }
}