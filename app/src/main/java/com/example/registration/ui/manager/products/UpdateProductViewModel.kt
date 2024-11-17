package com.example.registration.ui.manager.products

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UpdateProductViewModel @Inject constructor(
    private val managerRepository: ManagerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val token = "Bearer ${getUserToken()}"
    fun updateProduct(
        productId: Int,
        name: String,
        desc: String,
        quantity: Int,
        price: Double
    ): Flow<Result<Unit>> {
        return managerRepository.updateProduct(token, productId, name, desc, quantity, price)
    }
}