package com.example.registration.ui.manager.adminMode.departments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.database.manager.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDepartsViewModel(private val managerRepository: ManagerRepository) : ViewModel() {

    private val _departAllArray = MutableLiveData<List<DepartmentDTO>>()
    val departAllArray: LiveData<List<DepartmentDTO>>
        get() = _departAllArray

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun getAllDepartments(): LiveData<List<DepartmentDTO>> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllDepartments()
            withContext(Dispatchers.Main) {
                _departAllArray.postValue(result)
            }
        }
        return departAllArray
    }

    fun removeDepartmentById(departmentId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            managerRepository.removeDepartmentById(departmentId)
            getAllDepartments()
        }
    }

}