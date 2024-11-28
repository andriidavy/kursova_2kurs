package com.example.registration.ui.manager.products

import androidx.lifecycle.viewModelScope
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.manager.ManagerRepository
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
class AllProductListViewModel @Inject constructor(
    private val managerRepository: ManagerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val token = "Bearer ${getUserToken()}"
    private val _productDTOArray = MutableStateFlow<List<ProductDTO>>(emptyList())
    val productDTOArray: StateFlow<List<ProductDTO>>
        get() = _productDTOArray

    var currentPage = 0

    private val pageSize = 10

    private var lastSearchStr = ""

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
            val result = managerRepository.getAllProducts(token, page, pageSize)
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

    fun loadNextPageBySearch() {
        if (!isLastPage()) {
            currentPage += 1
            viewModelScope.launch {
                delay(100)
                getProductsBySearch(lastSearchStr, 0, currentPage)
            }
        }
    }

    fun loadPreviousPageBySearch() {
        if (currentPage > 0) currentPage -= 1 else 0
        viewModelScope.launch {
            delay(100)
            getProductsBySearch(lastSearchStr, 0, currentPage)
        }
    }

    fun getProductsBySearch(searchStr: String, chooseType: Int, page: Int) {
        lastSearchStr = searchStr
        viewModelScope.launch(Dispatchers.IO) {
            val result =
                managerRepository.searchProduct(token, searchStr, chooseType, page, pageSize)
            withContext(Dispatchers.Main) {
                result.collect { pageResult ->
                    _productDTOArray.value = pageResult
                }
            }
        }
    }
}