package com.example.registration.ui.customer.custom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.database.customer.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddDepartForNewCustomViewModel @Inject constructor(private val customerRepository: CustomerRepository) :
    ViewModel() {

    private val _departDTOArray = MutableLiveData<List<DepartmentDTO>>()
    val departDTOArray: LiveData<List<DepartmentDTO>>
        get() = _departDTOArray
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    init {
        getAllDepartments()
    }

    private fun getAllDepartments() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.getAllDepartments()
            withContext(Dispatchers.Main) {
                _departDTOArray.value = result
            }
        }
    }

    fun assignDepartmentToCustom(customId: Int, departmentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            customerRepository.assignDepartmentToCustom(customId, departmentId)
        }
        _message.value = "Відділ доставки призначено!"
    }

}