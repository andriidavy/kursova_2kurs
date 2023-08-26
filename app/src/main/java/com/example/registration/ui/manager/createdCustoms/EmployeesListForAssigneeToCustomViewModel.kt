package com.example.registration.ui.manager.createdCustoms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EmployeesListForAssigneeToCustomViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    private val _employeeDTOArray = MutableStateFlow<List<EmployeeProfileDTO>>(emptyList())
    val employeeDTOArray: StateFlow<List<EmployeeProfileDTO>>
        get() = _employeeDTOArray

    init {
        getAllEmployeesProfile()
    }

    private fun getAllEmployeesProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllEmployeesProfile()
            withContext(Dispatchers.Main) {
                result.collect {
                    _employeeDTOArray.value = it
                }
            }
        }
    }

    fun assignEmployeeToCustom(customId: Int, employeeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.assignEmployeeToCustom(customId, employeeId)
        }
    }
}