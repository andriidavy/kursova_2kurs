package com.example.registration.myISAM.ui.customer.cart

import androidx.lifecycle.viewModelScope
import com.example.registration.database.myIsam.customer.MiCustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.cart.CartProductDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiCustomerCartPageViewModel  @Inject constructor(
    private val miCustomerRepository: MiCustomerRepository, datastoreRepository: DatastoreRepo,
) : DataStoreViewModel(datastoreRepository) {

    private val _cartProductsArrayDTO = MutableStateFlow<List<CartProductDTO>>(emptyList())
    val cartProductsArrayDTO: StateFlow<List<CartProductDTO>>
        get() = _cartProductsArrayDTO
    private val customerId = getUserId()

    init {
        getAllCartProducts()
    }

    private fun getAllCartProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miCustomerRepository.getCartProducts(customerId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _cartProductsArrayDTO.value = it
                }
            }
        }
    }

    fun removeProductFromCart(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            miCustomerRepository.removeProductFromCart(customerId, productId)
            withContext(Dispatchers.Main) {
                getAllCartProducts()
            }
        }
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            miCustomerRepository.clearCart(customerId)
            withContext(Dispatchers.Main) {
                getAllCartProducts()
            }
        }
    }
}