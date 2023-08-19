package com.example.registration.ui.customer.cart


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.registration.model.cart.CartProductDTO
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerCartPageViewModel @Inject constructor(
    private val customerRepository: CustomerRepository, datastoreRepository: DatastoreRepo,
) : DataStoreViewModel(datastoreRepository) {

    private val _cartProductsArrayDTO = MutableStateFlow<List<CartProductDTO>>(emptyList())
    val cartProductsArrayDTO: StateFlow<List<CartProductDTO>>
        get() = _cartProductsArrayDTO
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message
    private val customerId = getUserId()

    init {
        getAllCartProducts()
    }

    private fun getAllCartProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCartProducts(customerId)
            withContext(Dispatchers.Main) {
                _cartProductsArrayDTO.value = result
            }
        }
    }

    fun createCustom(): Flow<Result<Int>> = flow {
        emit(customerRepository.createCustom(customerId))
    }

    fun removeProductFromCart(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.removeProductFromCart(customerId, productId)
        }
        _message.value = "Товар видалено з корзини"
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.clearCart(customerId)
        }
        _message.value = "Корзину очищено"
    }
}