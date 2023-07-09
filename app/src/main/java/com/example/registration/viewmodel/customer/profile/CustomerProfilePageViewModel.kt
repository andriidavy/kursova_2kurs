package com.example.registration.viewmodel.customer.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.CustomerProfileDTO
import com.example.registration.database.customer.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerProfilePageViewModel(val customerRepository: CustomerRepository) : ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _customerProfileDTO = MutableLiveData<CustomerProfileDTO>()
    val customerProfileDTO: LiveData<CustomerProfileDTO>
        get() = _customerProfileDTO

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val customerId: Int
        get() = sharedPreferences.getInt("customerId", 0)

    fun getCustomerProfileById() : LiveData<CustomerProfileDTO> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCustomerProfileById(customerId)
            withContext(Dispatchers.Main) {
                _customerProfileDTO.value = result
            }
        }
        return customerProfileDTO
    }

}