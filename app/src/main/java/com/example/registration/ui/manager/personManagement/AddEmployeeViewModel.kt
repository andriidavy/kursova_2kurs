package com.example.registration.ui.manager.personManagement

import androidx.lifecycle.ViewModel
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.model.users.Employee
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEmployeeViewModel @Inject constructor(private val managerRepository: ManagerRepository) :
    ViewModel() {

    fun addEmployee(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Flow<Result<Employee>> = flow {
        val result = managerRepository.saveEmployee(name, surname, email, password)
        withContext(Dispatchers.Main) {
            emit(result)
        }
    }.flowOn(Dispatchers.IO)
}