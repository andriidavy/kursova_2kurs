package com.example.registration.ui.customer.mainpage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProductItemViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    dataStoreViewModel: DataStoreViewModel
) : ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message
    private val customerId: Int = dataStoreViewModel.getUserId()

    fun addProductToCart(productId: Int, quantity: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.addProductToCart(customerId, productId, quantity)
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    _message.value = "Додано до корзини"
                }.onFailure { exception ->
                    _message.value = "Помилка: ${exception.message}"
                }
            }
        }
    }

    fun setMessage(message: String) {
        _message.value = message
    }
}