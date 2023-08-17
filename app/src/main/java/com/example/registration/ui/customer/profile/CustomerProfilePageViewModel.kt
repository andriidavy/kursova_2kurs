package com.example.registration.ui.customer.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.CustomerProfileDTO
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerProfilePageViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    dataStoreViewModel: DataStoreViewModel
) : ViewModel() {

    private val _customer = MutableStateFlow(CustomerProfileDTO())
    val customer: StateFlow<CustomerProfileDTO>
        get() = _customer
    private val customerId = dataStoreViewModel.getUserId()

    init {
        getCustomerProfileById()
    }

    private fun getCustomerProfileById() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCustomerProfileById(customerId)

            withContext(Dispatchers.Main) {
                _customer.value = result
            }
        }
    }
}