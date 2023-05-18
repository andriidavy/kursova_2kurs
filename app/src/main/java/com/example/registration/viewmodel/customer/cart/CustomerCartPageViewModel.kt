package com.example.registration.viewmodel.customer.cart

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.cart.CartProductDTO
import com.example.registration.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerCartPageViewModel(private val customerRepository: CustomerRepository) : ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _cartProductsArrayDTO = MutableLiveData<List<CartProductDTO>>()
    val cartProductsArrayDTO: LiveData<List<CartProductDTO>>
        get() = _cartProductsArrayDTO

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message


    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val customerId: Int
        get() = sharedPreferences.getInt("customerId", 0)

    fun getAllCartProducts(): LiveData<List<CartProductDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCartProducts(customerId)
            withContext(Dispatchers.Main) {
                _cartProductsArrayDTO.value = result
            }
        }
        return cartProductsArrayDTO
    }

    fun createCustom(){
        viewModelScope.launch(Dispatchers.IO){
            customerRepository.createCustom(customerId)
        }
        _message.value = "Замовлення створено"
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.clearCart(customerId)
        }
        _message.value = "Корзину очищено"
    }
}