package com.example.registration.viewmodel.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.product.Product
import com.example.registration.repository.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerMainPageViewModel(
    private val customerRepository: CustomerRepository,
) : ViewModel() {

    private val _productsArray = MutableLiveData<List<Product>>()
    val productsArray: LiveData<List<Product>>
        get() = _productsArray

    fun getAllProducts(): LiveData<List<Product>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getProducts()
            withContext(Dispatchers.Main) {
                _productsArray.value = result
            }
        }
        return productsArray
    }
}