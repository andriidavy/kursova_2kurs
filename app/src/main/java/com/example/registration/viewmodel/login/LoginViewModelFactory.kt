package com.example.registration.viewmodel.login

import com.example.registration.repository.CustomerRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.registration.repository.EmployeeRepository
import com.example.registration.repository.ManagerRepository

class LoginViewModelFactory(
    private val customerRepository: CustomerRepository,
    private val employeeRepository: EmployeeRepository,
    private val managerRepository: ManagerRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(customerRepository, employeeRepository,managerRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}