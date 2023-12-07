package com.example.registration.ui.customer.productsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.product.ProductDTO
import com.example.registration.database.customer.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerProductsListViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {
    private val _productsArray = MutableStateFlow<List<ProductDTO>>(emptyList())
    val productsArray: StateFlow<List<ProductDTO>>
        get() = _productsArray

    private val _minPrice = MutableStateFlow(0.0)
    val minPrice: StateFlow<Double>
        get() = _minPrice

    private val _maxPrice = MutableStateFlow(0.0)
    val maxPrice: StateFlow<Double>
        get() = _maxPrice

    init {
        getAllProducts()
        getMinPrice()
        getMaxPrice()
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getProducts()
            withContext(Dispatchers.Main) {
                result.collect {
                    _productsArray.value = it
                }
            }
        }
    }

    fun getProductsBySearch(searchStr: String, chooseType: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.searchProduct(searchStr, chooseType)
            withContext(Dispatchers.Main) {
                result.collect {
                    _productsArray.value = it
                }
            }
        }
    }

    fun getProductsBySearchWithPriceRange(searchStr: String, chooseType: Int, minPrice: Double, maxPrice: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.searchProductWithPriceRange(searchStr, chooseType, minPrice, maxPrice)
            withContext(Dispatchers.Main) {
                result.collect {
                    _productsArray.value = it
                }
            }
        }
    }

    private fun getMinPrice(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getMinProductPrice()
            withContext(Dispatchers.Main){
                result.collect{
                    _minPrice.value = it
                }
            }
        }
    }

    private fun getMaxPrice(){
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getMaxProductPrice()
            withContext(Dispatchers.Main){
                result.collect{
                    _maxPrice.value = it
                }
            }
        }
    }
}