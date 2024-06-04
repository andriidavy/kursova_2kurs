package com.example.registration.ui.manager.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.product.ProductDTO
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllProductListViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    private val _productDTOArray = MutableStateFlow<List<ProductDTO>>(emptyList())
    val productDTOArray: StateFlow<List<ProductDTO>>
        get() = _productDTOArray

    var currentPage = 0

    private val pageSize = 10

    init {
        viewModelScope.launch {
            delay(200)
            getAllProductsPage(0)
        }
    }

    fun isLastPage(): Boolean {
        return _productDTOArray.value.size < pageSize
    }

    fun getAllProductsPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllProducts(page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect {
                    _productDTOArray.value = it
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLastPage()) {
            currentPage += 1
            viewModelScope.launch {
                delay(100)
                getAllProductsPage(currentPage)

            }
        }
    }

    fun loadPreviousPage() {
        if (currentPage > 0) currentPage -= 1 else 0
        viewModelScope.launch {
            delay(100)
            getAllProductsPage(currentPage)
        }
    }

    fun searchProductById(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.searchProductById(productId)
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