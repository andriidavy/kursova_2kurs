package com.example.registration.UI.login

import com.example.registration.database.customer.CustomerRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.manager.ManagerRepository

//class LoginViewModelFactory(
//    private val customerRepository: CustomerRepository,
//    private val employeeRepository: EmployeeRepository,
//    private val managerRepository: ManagerRepository
//) : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return LoginViewModel(customerRepository, employeeRepository,managerRepository) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}