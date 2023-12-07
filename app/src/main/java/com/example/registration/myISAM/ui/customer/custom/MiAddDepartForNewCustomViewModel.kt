package com.example.registration.myISAM.ui.customer.custom

import androidx.lifecycle.viewModelScope
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.myIsam.customer.MiCustomerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.department.DepartmentDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiAddDepartForNewCustomViewModel @Inject constructor(
    private val miCustomerRepository: MiCustomerRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _departDTOArray = MutableStateFlow<List<DepartmentDTO>>(emptyList())
    val departDTOArray: StateFlow<List<DepartmentDTO>>
        get() = _departDTOArray
    private val customerId = getUserId()

    init {
        getAllDepartments()
    }

    private fun getAllDepartments() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miCustomerRepository.getAllDepartments()
            withContext(Dispatchers.Main) {
                _departDTOArray.value = result
            }
        }
    }

    fun createCustom(departmentId: Int): Flow<Result<Int>> {
        return miCustomerRepository.createCustom(customerId, departmentId);
    }
}