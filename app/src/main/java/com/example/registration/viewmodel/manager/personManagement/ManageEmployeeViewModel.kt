package com.example.registration.viewmodel.manager.personManagement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.database.manager.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManageEmployeeViewModel(private val managerRepository: ManagerRepository) : ViewModel() {

    private val _employeeDTOArray = MutableLiveData<List<EmployeeProfileDTO>>()
    val employeeDTOArray: LiveData<List<EmployeeProfileDTO>>
        get() = _employeeDTOArray

    fun getAllEmployeesProfile(): LiveData<List<EmployeeProfileDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllEmployeesProfile()
            withContext(Dispatchers.Main) {
                _employeeDTOArray.postValue(result)
            }
        }
        return employeeDTOArray
    }

    fun deleteEmployeeById(employeeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.deleteEmployeeById(employeeId)
            getAllEmployeesProfile()
        }
    }

}