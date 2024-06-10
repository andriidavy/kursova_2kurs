package com.example.registration.ui.manager.products

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UpdateProductViewModel @Inject constructor(private val managerRepository: ManagerRepository) : ViewModel()  {
    fun updateProduct(productId: Int, name: String, desc: String, quantity: Int, price: Double): Flow<Result<Unit>> {
        return managerRepository.updateProduct(productId, name, desc, quantity, price)
    }
}