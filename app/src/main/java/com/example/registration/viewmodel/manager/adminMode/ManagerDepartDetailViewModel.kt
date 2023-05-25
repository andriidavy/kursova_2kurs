package com.example.registration.viewmodel.manager.adminMode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ManagerDepartDetailViewModel(private val managerRepository: ManagerRepository): ViewModel() {

    private val _departForManagerArray = MutableLiveData<List<DepartmentDTO>>()
    val departForManagerArray: LiveData<List<DepartmentDTO>>
        get() = _departForManagerArray



    fun getAllDepartmentsForManager(managerId : Int): LiveData<List<DepartmentDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllDepartmentsForManager(managerId)
            withContext(Dispatchers.Main) {
                _departForManagerArray.postValue(result)
            }
        }
        return departForManagerArray
    }

    fun removeDepartmentFromManager(managerId: Int, departmentId: Int){
        viewModelScope.launch(Dispatchers.IO){
            managerRepository.removeDepartmentFromManager(managerId, departmentId)
            getAllDepartmentsForManager(managerId)
        }
    }
}