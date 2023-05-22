package com.example.registration.viewmodel.manager.products

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.product.Product
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class addProductViewModel(private val managerRepository: ManagerRepository) : ViewModel() {

    val message = MutableLiveData<String>()

    fun saveProduct(name: String, description: String, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.saveProduct(
                Product(
                    name,
                    description,
                    quantity
                )
            )
        }
        message.value = "Товар додано до бази данних!"
    }
}