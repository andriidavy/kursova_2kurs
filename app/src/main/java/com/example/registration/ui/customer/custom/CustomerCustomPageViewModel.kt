package com.example.registration.ui.customer.custom

import androidx.lifecycle.viewModelScope
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.custom.CustomDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerCustomPageViewModel @Inject constructor(
    private val customerRepository: CustomerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _customDTOArray = MutableStateFlow<List<CustomDTO>>(emptyList())
    val customDTOArray: StateFlow<List<CustomDTO>>
        get() = _customDTOArray

    private val customerId: Int = getUserId()

    fun getCustomsForCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCustomsForCustomer(customerId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _customDTOArray.value = it
                }
            }
        }
    }
}