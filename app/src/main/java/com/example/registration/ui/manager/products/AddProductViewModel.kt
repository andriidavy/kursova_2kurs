package com.example.registration.ui.manager.products

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val managerRepository: ManagerRepository) : ViewModel() {

    fun provideProduct(name: String, quantity: Int, price: Double, description: String): Flow<Result<Int>> {
        return managerRepository.provideProduct(name, quantity, price, description)
    }

    fun isProductExists(productName: String): Flow<Result<Boolean>> {
        return managerRepository.isProductExists(productName)
    }
}