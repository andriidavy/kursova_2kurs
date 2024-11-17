package com.example.registration.ui.manager.personManagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.EmployeeProfileDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ManageEmployeeViewModel @Inject constructor(
    private val managerRepository: ManagerRepository, datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val token = "Bearer ${getUserToken()}"

    private val _employeeDTOArray = MutableStateFlow<List<EmployeeProfileDTO>>(emptyList())
    val employeeDTOArray: StateFlow<List<EmployeeProfileDTO>>
        get() = _employeeDTOArray

    init {
        getAllEmployeesProfile()
    }

    fun getAllEmployeesProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllEmployeesProfile(token)
            withContext(Dispatchers.Main) {
                result.collect { resultList ->
                    _employeeDTOArray.value = resultList.sortedByDescending { it.id }
                }
            }
        }
    }

    fun deleteEmployeeById(employeeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.deleteEmployeeById(token, employeeId)
            withContext(Dispatchers.Main) {
                getAllEmployeesProfile()
            }
        }
    }
}