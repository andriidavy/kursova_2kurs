package com.example.registration.myISAM.ui.manager.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.myIsam.manager.MiManagerRepository
import com.example.registration.model.product.ProductDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiAllProductListViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    private val _productDTOArray = MutableStateFlow<List<ProductDTO>>(emptyList())
    val productDTOArray: StateFlow<List<ProductDTO>>
        get() = _productDTOArray

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miManagerRepository.getAllProducts()
            withContext(Dispatchers.Main) {
                result.collect {
                    _productDTOArray.value = it
                }
            }
        }
    }

    fun searchProductById(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miManagerRepository.searchProductById(productId)
            withContext(Dispatchers.Main) {
                result.collect { result ->
                    result.onSuccess { product ->
                        _productDTOArray.value = listOf(product)
                    }
                    result.onFailure {
                        _productDTOArray.value = emptyList()
                    }
                }
            }
        }
    }
}