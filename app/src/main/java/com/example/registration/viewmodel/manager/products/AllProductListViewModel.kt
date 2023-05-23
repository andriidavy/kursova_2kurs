package com.example.registration.viewmodel.manager.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.product.Product
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllProductListViewModel(private val managerRepository: ManagerRepository) : ViewModel() {

    private val _productArray = MutableLiveData<List<Product>>()
    val productArray: LiveData<List<Product>>
        get() = _productArray

    fun getAllProducts(): LiveData<List<Product>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllProducts()
            withContext(Dispatchers.Main) {
                _productArray.postValue(result)
            }
        }
        return productArray
    }
}