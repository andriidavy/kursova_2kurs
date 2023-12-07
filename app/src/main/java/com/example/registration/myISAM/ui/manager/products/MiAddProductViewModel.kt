package com.example.registration.myISAM.ui.manager.products

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.myIsam.manager.MiManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MiAddProductViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) : ViewModel() {

    fun provideProduct(name: String, quantity: Int, price: Double, description: String): Flow<Result<Int>> {
        return miManagerRepository.provideProduct(name, quantity, price, description)
    }

    fun isProductExists(productName: String): Flow<Result<Boolean>> {
        return miManagerRepository.isProductExists(productName)
    }
}