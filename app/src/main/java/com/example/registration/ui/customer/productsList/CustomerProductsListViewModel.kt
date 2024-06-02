package com.example.registration.ui.customer.productsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.product.ProductDTO
import com.example.registration.database.customer.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.max

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

    private val _currentPage = MutableStateFlow(0)
    val currentPage: StateFlow<Int> get() = _currentPage

    private val pageSize = 10

    private var lastSearchStr = ""
    private var lastChooseType = 0
    private var lastMinPrice = 0.0
    private var lastMaxPrice = 0.0

    init {
        viewModelScope.launch {
            delay(100)
            getMinPrice()
            getMaxPrice()
            getAllProductsPage(0)
        }
    }

    fun isLastProductsList(): Boolean {
        return _productsArray.value.size < pageSize
    }

    fun getAllProductsPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getProductsPage(page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect { pageResult ->
                    _productsArray.value = pageResult
                    _currentPage.value = page
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLastProductsList()) {
            getAllProductsPage(_currentPage.value + 1)
        }
    }

    fun loadPreviousPage() {
        val previousPage = if (_currentPage.value > 0) _currentPage.value - 1 else 0
        getAllProductsPage(previousPage)
    }

    fun getProductsBySearch(searchStr: String, chooseType: Int, page: Int) {
        lastSearchStr = searchStr
        lastChooseType = chooseType
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.searchProduct(searchStr, chooseType, page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect {
                    _productsArray.value = it
                    _currentPage.value = page
                }
            }
        }
    }

    fun loadNextPageBySearch() {
        if (!isLastProductsList()) {
            getProductsBySearch(lastSearchStr, lastChooseType, _currentPage.value + 1)
        }
    }

    fun loadPreviousPageBySearch() {
        val previousPage = if (_currentPage.value > 0) _currentPage.value - 1 else 0
        getProductsBySearch(lastSearchStr, lastChooseType, previousPage)
    }

    fun getProductsBySearchWithPriceRange(
        searchStr: String,
        chooseType: Int,
        minPrice: Double,
        maxPrice: Double,
        page: Int
    ) {
        lastSearchStr = searchStr
        lastChooseType = chooseType
        lastMinPrice = minPrice
        lastMaxPrice = maxPrice
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.searchProductWithPriceRange(
                searchStr,
                chooseType,
                minPrice,
                maxPrice,
                page,
                pageSize
            )
            withContext(Dispatchers.Main) {
                result.collect {
                    _productsArray.value = it
                    _currentPage.value = page
                }
            }
        }
    }

    fun loadNextPageBySearchWithPrice() {
        if (!isLastProductsList()) {
            getProductsBySearchWithPriceRange(
                lastSearchStr,
                lastChooseType,
                lastMinPrice,
                lastMaxPrice,
                _currentPage.value + 1
            )
        }
    }

    fun loadPreviousPageBySearchWithPrice() {
        val previousPage = if (_currentPage.value > 0) _currentPage.value - 1 else 0
        getProductsBySearchWithPriceRange(
            lastSearchStr,
            lastChooseType,
            lastMinPrice,
            lastMaxPrice,
            previousPage
        )
    }

    private fun getMinPrice() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getMinProductPrice()
            withContext(Dispatchers.Main) {
                result.collect {
                    _minPrice.value = it
                }
            }
        }
    }

    private fun getMaxPrice() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getMaxProductPrice()
            withContext(Dispatchers.Main) {
                result.collect {
                    _maxPrice.value = it
                }
            }
        }
    }
}