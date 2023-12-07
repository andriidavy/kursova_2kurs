package com.example.registration.myISAM.ui.customer.productsList

import com.example.registration.database.myIsam.customer.MiCustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MiProductItemViewModel @Inject constructor(
    private val miCustomerRepository: MiCustomerRepository, datastoreRepository: DatastoreRepo,
) : DataStoreViewModel(datastoreRepository) {
    private val customerId: Int = getUserId()

    fun addProductToCart(productId: Int, quantity: Int): Flow<Result<Unit>> {
        return miCustomerRepository.addProductToCart(customerId, productId, quantity)
    }
}