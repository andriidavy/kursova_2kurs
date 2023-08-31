package com.example.registration.ui.manager.adminMode.managers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AllDepartViewModel @Inject constructor(
    private val managerRepository: ManagerRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _departNonForManagerArray = MutableStateFlow<List<DepartmentDTO>>(emptyList())
    val departNonForManagerArray: StateFlow<List<DepartmentDTO>>
        get() = _departNonForManagerArray
    private val managerId = getUserId()

    init {
        getDepartmentsWithoutManager()
    }

    fun getDepartmentsWithoutManager() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.getDepartmentsWithoutManager(managerId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _departNonForManagerArray.value = it
                }
            }
        }
    }

    fun assignDepartmentToManager(departmentId: Int): Flow<Result<Unit>> {
        return managerRepository.assignDepartmentToManager(managerId, departmentId)
    }
}