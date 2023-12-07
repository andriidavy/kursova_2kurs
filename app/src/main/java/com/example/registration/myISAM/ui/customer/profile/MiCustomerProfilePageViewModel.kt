package com.example.registration.myISAM.ui.customer.profile

import androidx.lifecycle.viewModelScope
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.myIsam.customer.MiCustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.CustomerProfileDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiCustomerProfilePageViewModel  @Inject constructor(
    private val miCustomerRepository: MiCustomerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _customer = MutableStateFlow(CustomerProfileDTO())
    val customer: StateFlow<CustomerProfileDTO>
        get() = _customer
    private val customerId = getUserId()

    init {
        getCustomerProfileById()
    }

    private fun getCustomerProfileById() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miCustomerRepository.getCustomerProfileById(customerId)

            withContext(Dispatchers.Main) {
                _customer.value = result
            }
        }
    }
}