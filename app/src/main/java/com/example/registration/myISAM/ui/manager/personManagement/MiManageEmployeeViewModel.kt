package com.example.registration.myISAM.ui.manager.personManagement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.myIsam.manager.MiManagerRepository
import com.example.registration.model.users.EmployeeProfileDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MiManageEmployeeViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    private val _employeeDTOArray = MutableStateFlow<List<EmployeeProfileDTO>>(emptyList())
    val employeeDTOArray: StateFlow<List<EmployeeProfileDTO>>
        get() = _employeeDTOArray

    init {
        getAllEmployeesProfile()
    }

    private fun getAllEmployeesProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = miManagerRepository.getAllEmployeesProfile()
            withContext(Dispatchers.Main) {
                result.collect {
                    _employeeDTOArray.value = it
                }
            }
        }
    }

    fun deleteEmployeeById(employeeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            miManagerRepository.deleteEmployeeById(employeeId)
            withContext(Dispatchers.Main) {
                getAllEmployeesProfile()
            }
        }
    }
}