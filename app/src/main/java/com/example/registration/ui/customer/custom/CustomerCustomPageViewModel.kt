package com.example.registration.ui.customer.custom

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.database.customer.CustomerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomerCustomPageViewModel(private val customerRepository: CustomerRepository) :
    ViewModel() {
    private lateinit var sharedPreferences: SharedPreferences

    private val _customDTOArray = MutableLiveData<List<CustomDTO>>()
    val customDTOArray: LiveData<List<CustomDTO>>
        get() = _customDTOArray

    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    val customerId: Int
        get() = sharedPreferences.getInt("customerId", 0)

    fun getCustomsForCustomer(): LiveData<List<CustomDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getCustomsForCustomer(customerId)
            withContext(Dispatchers.Main) {
                _customDTOArray.value = result
            }
        }
        return customDTOArray
    }
}