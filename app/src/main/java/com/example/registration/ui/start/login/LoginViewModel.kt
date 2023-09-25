package com.example.registration.ui.start.login

import androidx.lifecycle.ViewModel
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val employeeRepository: EmployeeRepository,
    private val managerRepository: ManagerRepository
) : ViewModel() {

   fun login(email: String, password: String, num: Int): Flow<Result<User>>? {
        val result = when (num) {
            0 -> customerRepository.loginCustomer(email, password)
            1 -> employeeRepository.loginEmployee(email, password)
            2 -> managerRepository.loginManager(email, password)
            else -> null
        }
        return result
    }
}

