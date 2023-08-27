package com.example.registration.ui.manager.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.product.Product
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllProductListViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    private val _productArray = MutableStateFlow<List<Product>>(emptyList())
    val productArray: StateFlow<List<Product>>
        get() = _productArray

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllProducts()
            withContext(Dispatchers.Main) {
                result.collect {
                    _productArray.value = it
                }
            }
        }
    }
}