package com.example.registration.ui.manager.products

import androidx.lifecycle.ViewModel
import com.example.registration.model.product.Product
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val managerRepository: ManagerRepository) : ViewModel() {

    suspend fun saveProduct(name: String, description: String, quantity: Int) {
        managerRepository.saveProduct(
            Product(
                name,
                description,
                quantity
            )
        )
    }
}