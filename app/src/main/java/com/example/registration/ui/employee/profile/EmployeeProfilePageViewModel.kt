package com.example.registration.ui.employee.profile

import androidx.lifecycle.viewModelScope
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.users.EmployeeProfileDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EmployeeProfilePageViewModel @Inject constructor(
    private val employeeRepository: EmployeeRepository,
    datastoreRepository: DatastoreRepo
) : DataStoreViewModel(datastoreRepository) {

    private val _employeeProfileDTO = MutableStateFlow(EmployeeProfileDTO())
    val employeeProfileDTO: StateFlow<EmployeeProfileDTO>
        get() = _employeeProfileDTO
    private val employeeId = getUserId()

    init {
        getEmployeeProfileById()
    }

    private fun getEmployeeProfileById() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = employeeRepository.getEmployeeProfile(employeeId)
            withContext(Dispatchers.Main) {
                result.collect {
                    _employeeProfileDTO.value = it
                }
            }
        }
    }
}