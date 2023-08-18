package com.example.registration.ui.customer.custom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.database.customer.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddDepartForNewCustomViewModel @Inject constructor(private val customerRepository: CustomerRepository) :
    ViewModel() {

    private val _departDTOArray = MutableStateFlow<List<DepartmentDTO>>(emptyList())
    val departDTOArray: StateFlow<List<DepartmentDTO>>
        get() = _departDTOArray

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
    }
}