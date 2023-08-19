package com.example.registration.ui.customer.mainpage

import androidx.lifecycle.ViewModel
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class ProductItemViewModel @Inject constructor(
    private val customerRepository: CustomerRepository, datastoreRepository: DatastoreRepo,
) : DataStoreViewModel(datastoreRepository) {
    private val customerId: Int = getUserId()

    fun addProductToCart(productId: Int, quantity: Int): Flow<Result<Unit>> = flow {
        emit(customerRepository.addProductToCart(customerId, productId, quantity))
    }.flowOn(Dispatchers.IO)
}