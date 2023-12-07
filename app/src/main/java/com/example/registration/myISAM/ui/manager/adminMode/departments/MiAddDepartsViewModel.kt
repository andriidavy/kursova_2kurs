package com.example.registration.myISAM.ui.manager.adminMode.departments

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.myIsam.manager.MiManagerRepository
import com.example.registration.model.department.DepartmentDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MiAddDepartsViewModel @Inject constructor(private val miManagerRepository: MiManagerRepository) :
    ViewModel() {

    fun saveDepart(departmentName: String): Flow<Result<Unit>> {
        return miManagerRepository.saveDepartment(departmentName)
    }
}