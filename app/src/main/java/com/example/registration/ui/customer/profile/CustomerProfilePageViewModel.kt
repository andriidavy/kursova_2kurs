package com.example.registration.ui.customer.profile

import androidx.lifecycle.ViewModel
import com.example.registration.model.users.CustomerProfileDTO
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class CustomerProfilePageViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    dataStoreViewModel: DataStoreViewModel
) : ViewModel() {

    private val customerId = dataStoreViewModel.getUserId()

    fun getCustomerProfileById(): Flow<CustomerProfileDTO> = flow {
        emit(customerRepository.getCustomerProfileById(customerId))
    }.flowOn(Dispatchers.IO)
}