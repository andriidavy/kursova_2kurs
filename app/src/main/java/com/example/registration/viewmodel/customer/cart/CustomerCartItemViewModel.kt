package com.example.registration.viewmodel.customer.cart

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CustomerCartItemViewModel(private val customerRepository: CustomerRepository): ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val customerId: Int
        get() = sharedPreferences.getInt("customerId", 0)

    fun removeProductFromCart (productId:Int){
        viewModelScope.launch(Dispatchers.IO){
            customerRepository.removeProductFromCart(customerId, productId)
        }
    }
}