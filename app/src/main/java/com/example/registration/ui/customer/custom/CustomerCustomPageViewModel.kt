package com.example.registration.ui.customer.custom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.datastore.Constants
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerCustomPageViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val dataStoreRepository: DatastoreRepo
) :
    ViewModel() {

    private val _customDTOArray = MutableLiveData<List<CustomDTO>>()
    val customDTOArray: LiveData<List<CustomDTO>>
        get() = _customDTOArray

    private val customerId: Int = runBlocking {
        dataStoreRepository.getInt(Constants.USER_ID)!!
    }

    fun getCustomsForCustomer() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCustomsForCustomer(customerId)
            withContext(Dispatchers.Main) {
                _customDTOArray.value = result
            }
        }
    }
}