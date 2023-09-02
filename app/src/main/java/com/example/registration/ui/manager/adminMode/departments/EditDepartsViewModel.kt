package com.example.registration.ui.manager.adminMode.departments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
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
class EditDepartsViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    private val _departAllArray = MutableStateFlow<List<DepartmentDTO>>(emptyList())
    val departAllArray: StateFlow<List<DepartmentDTO>>
        get() = _departAllArray

    init {
        getAllDepartments()
    }

    fun getAllDepartments() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllDepartments()
            withContext(Dispatchers.Main) {
                result.collect {
                    _departAllArray.value = it
                }
            }
        }
    }

    fun removeDepartmentById(departmentId: Int): Flow<Result<Unit>> {
        return managerRepository.removeDepartmentById(departmentId)
    }
}