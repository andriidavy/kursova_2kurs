package com.example.registration.ui.customer.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.CustomerProfileDTO
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.Constants
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
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