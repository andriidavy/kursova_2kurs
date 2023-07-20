package com.example.registration.ui.customer.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.product.Product
import com.example.registration.database.customer.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerMainPageViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
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