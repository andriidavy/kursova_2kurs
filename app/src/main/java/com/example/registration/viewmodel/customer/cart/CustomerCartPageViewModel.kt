package com.example.registration.viewmodel.customer.cart

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.cart.CartProduct
import com.example.registration.model.users.Customer
import com.example.registration.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerCartPageViewModel(private val customerRepository: CustomerRepository) : ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _cartProductsArray = MutableLiveData<List<CartProduct>>()
    val cartProductsArray: LiveData<List<CartProduct>>
        get() = _cartProductsArray


    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val customerId: Int
        get() = sharedPreferences.getInt("customerId", 0)

    fun getAllCartProducts(): LiveData<List<CartProduct>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCartProducts(customerId)
            withContext(Dispatchers.Main) {
                _cartProductsArray.value = result
            }
        }
        return cartProductsArray
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.clearCart(customerId)
        }
    }
}