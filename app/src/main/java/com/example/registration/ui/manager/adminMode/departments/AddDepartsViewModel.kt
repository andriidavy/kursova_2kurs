package com.example.registration.ui.manager.adminMode.departments

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.model.department.DepartmentDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AddDepartsViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    fun saveDepart(departmentName: String): Flow<Result<Unit>> {
        return managerRepository.saveDepartment(departmentName)
    }
}