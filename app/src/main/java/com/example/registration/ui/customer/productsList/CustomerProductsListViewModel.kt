package com.example.registration.ui.customer.productsList

import androidx.lifecycle.viewModelScope
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.product.ProductDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerProductsListViewModel @Inject constructor(
    private val customerRepository: CustomerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {
    private val _productsArray = MutableStateFlow<List<ProductDTO>>(emptyList())
    val productsArray: StateFlow<List<ProductDTO>>
        get() = _productsArray

    private val _cartCount = MutableStateFlow(0)
    val cartCount: StateFlow<Int>
        get() = _cartCount

    private val _minPrice = MutableStateFlow(0.0)
    val minPrice: StateFlow<Double>
        get() = _minPrice

    private val _maxPrice = MutableStateFlow(0.0)
    val maxPrice: StateFlow<Double>
        get() = _maxPrice

    var currentPage = 0

    private val pageSize = 10

    private var lastSearchStr = ""
    private var lastChooseType = 0
    private var lastMinPrice = 0.0
    private var lastMaxPrice = 0.0

    private val customerId = getUserId()

    init {
        viewModelScope.launch {
            getMinPrice()
            getMaxPrice()
            delay(100)
            getCartCount()
            delay(100)
            getAllProductsPage(0)
        }
    }

    fun isLastProductsList(): Boolean {
        return _productsArray.value.size < pageSize
    }

    fun getCartCount() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCartProducts(customerId)
            withContext(Dispatchers.Main) {
                result.collect { list ->
                    _cartCount.value = list.size
                }
            }
        }
    }

    fun getAllProductsPage(page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getProductsPage(page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect { pageResult ->
                    _productsArray.value = pageResult
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLastProductsList()) {
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

    fun getProductsBySearch(searchStr: String, chooseType: Int, page: Int) {
        lastSearchStr = searchStr
        lastChooseType = chooseType
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.searchProduct(searchStr, chooseType, page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect {pageResult->
                    _productsArray.value = pageResult
                }
            }
        }
    }

    fun loadNextPageBySearch() {
        if (!isLastProductsList()) {
            currentPage += 1
            viewModelScope.launch {
                delay(100)
                getProductsBySearch(lastSearchStr, lastChooseType, currentPage)
            }
        }
    }

    fun loadPreviousPageBySearch() {
        if (currentPage > 0) currentPage -= 1 else 0
        viewModelScope.launch {
            delay(100)
            getProductsBySearch(lastSearchStr, lastChooseType, currentPage)
        }
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
                }
            }
        }
    }

    fun loadNextPageBySearchWithPrice() {
        if (!isLastProductsList()) {
            currentPage += 1
            viewModelScope.launch {
                delay(100)
                getProductsBySearchWithPriceRange(
                    lastSearchStr,
                    lastChooseType,
                    lastMinPrice,
                    lastMaxPrice,
                    currentPage
                )
            }
        }
    }

    fun loadPreviousPageBySearchWithPrice() {
        if (currentPage > 0) currentPage -= 1 else 0
        getProductsBySearchWithPriceRange(
            lastSearchStr,
            lastChooseType,
            lastMinPrice,
            lastMaxPrice,
            currentPage
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