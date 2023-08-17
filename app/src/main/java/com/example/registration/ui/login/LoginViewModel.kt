package com.example.registration.ui.login

import com.example.registration.database.customer.CustomerRepository
import androidx.lifecycle.*
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val employeeRepository: EmployeeRepository,
    private val managerRepository: ManagerRepository
) : ViewModel() {

    fun login(email: String, password: String, num: Int): Flow<Result<User>?> = flow {
        val result = when (num) {
            0 -> customerRepository.loginCustomer(email, password)
            1 -> employeeRepository.loginEmployee(email, password)
            2 -> managerRepository.loginManager(email, password)
            else -> null
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}

