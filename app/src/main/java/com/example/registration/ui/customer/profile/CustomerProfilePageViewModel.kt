package com.example.registration.ui.customer.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.CustomerProfileDTO
import com.example.registration.database.customer.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerProfilePageViewModel @Inject constructor(val customerRepository: CustomerRepository) : ViewModel() {
    private val _customerProfileDTO = MutableLiveData<CustomerProfileDTO>()
    val customerProfileDTO: LiveData<CustomerProfileDTO>
        get() = _customerProfileDTO

    fun getCustomerProfileById(customerId: Int) : LiveData<CustomerProfileDTO> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCustomerProfileById(customerId)
            withContext(Dispatchers.Main) {
                _customerProfileDTO.value = result
            }
        }
        return customerProfileDTO
    }

}