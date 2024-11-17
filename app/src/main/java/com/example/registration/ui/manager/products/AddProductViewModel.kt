package com.example.registration.ui.manager.products

import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val managerRepository: ManagerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val token = "Bearer ${getUserToken()}"
    fun provideProduct(
        name: String,
        quantity: Int,
        price: Double,
        description: String
    ): Flow<Result<Int>> {
        return managerRepository.provideProduct(token, name, quantity, price, description)
    }

    fun isProductExists(productName: String): Flow<Result<Boolean>> {
        return managerRepository.isProductExists(token, productName)
    }
}