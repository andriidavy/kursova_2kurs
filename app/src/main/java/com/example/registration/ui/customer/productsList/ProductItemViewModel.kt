package com.example.registration.ui.customer.productsList

import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ProductItemViewModel @Inject constructor(
    private val customerRepository: CustomerRepository, datastoreRepository: DatastoreRepo,
) : DataStoreViewModel(datastoreRepository) {
    private val customerId: Int = getUserId()

    fun addProductToCart(productId: Int, quantity: Int): Flow<Result<Unit>> {
        return customerRepository.addProductToCart(customerId, productId, quantity)
    }
}