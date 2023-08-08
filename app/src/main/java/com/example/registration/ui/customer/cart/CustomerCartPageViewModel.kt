package com.example.registration.ui.customer.cart


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.cart.CartProductDTO
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.Constants
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerCartPageViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    dataStoreViewModel: DataStoreViewModel
) : ViewModel() {

    private val _cartProductsArrayDTO = MutableLiveData<List<CartProductDTO>>()
    val cartProductsArrayDTO: LiveData<List<CartProductDTO>>
        get() = _cartProductsArrayDTO
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message
    private val customerId: Int = dataStoreViewModel.getUserId()

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

    fun createCustom(): Int? {
        var customId: Int? = null
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.createCustom(customerId)

            withContext(Dispatchers.Main) {
                result.onSuccess { orderId ->
                    customId = orderId
                    _message.value = "Замовлення створено!"
                }.onFailure {
                    _message.value = "Помилка обробки замовлення!"
                }
            }
        }
        return customId
    }

    fun removeProductFromCart(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.removeProductFromCart(customerId, productId)
            getAllCartProducts()
        }
        _message.value = "Товар видалено з корзини"
    }

    fun clearCart() {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.clearCart(customerId)
            getAllCartProducts()
        }
        _message.value = "Корзину очищено"
    }
}