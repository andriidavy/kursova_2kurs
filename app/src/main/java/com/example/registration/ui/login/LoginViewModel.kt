package com.example.registration.ui.login

import androidx.lifecycle.ViewModel
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.myIsam.customer.MiCustomerRepository
import com.example.registration.database.myIsam.employee.MiEmployeeRepository
import com.example.registration.database.myIsam.manager.MiManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val employeeRepository: EmployeeRepository,
    private val managerRepository: ManagerRepository,
    private val miCustomerRepository: MiCustomerRepository,
    private val miEmployeeRepository: MiEmployeeRepository,
    private val miManagerRepository: MiManagerRepository
) : ViewModel() {

    fun login(email: String, password: String, num: Int): Flow<Result<Int>>?{
        val result = when (num) {
            0 -> customerRepository.loginCustomer(email, password)
            1 -> employeeRepository.loginEmployee(email, password)
            2 -> managerRepository.loginManager(email, password)
            else -> null
        }
        return result
    }

    fun loginMi(email: String, password: String, num: Int): Flow<Result<Int>>?{
        val result = when (num) {
            0 -> miCustomerRepository.loginCustomer(email, password)
            1 -> miEmployeeRepository.loginEmployee(email, password)
            2 -> miManagerRepository.loginManager(email, password)
            else -> null
        }
        return result
    }
}

