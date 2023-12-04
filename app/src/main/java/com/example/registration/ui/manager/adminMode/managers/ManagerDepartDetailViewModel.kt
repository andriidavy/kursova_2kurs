package com.example.registration.ui.manager.adminMode.managers

import androidx.lifecycle.viewModelScope
import com.example.registration.database.manager.ManagerRepository
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
class ManagerDepartDetailViewModel @Inject constructor(
    private val managerRepository: ManagerRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _departForManagerArray = MutableStateFlow<List<DepartmentDTO>>(emptyList())
    val departForManagerArray: StateFlow<List<DepartmentDTO>>
        get() = _departForManagerArray

    fun getAllDepartmentsForManager(managerId : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getAllDepartmentsForManager(managerId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _departForManagerArray.value = it
                }
            }
        }
    }

    fun removeDepartmentFromManager(managerId: Int, departmentId: Int): Flow<Result<Unit>> {
            return managerRepository.removeDepartmentFromManager(managerId, departmentId)
    }
}