package com.example.registration.viewmodel.login

import android.content.Context
import android.content.SharedPreferences
import com.example.registration.repository.CustomerRepository
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.registration.R
import com.example.registration.model.users.Customer
import com.example.registration.model.users.Employee
import com.example.registration.model.users.Manager
import com.example.registration.repository.EmployeeRepository
import com.example.registration.repository.ManagerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginViewModel(
    private val customerRepository: CustomerRepository,
    private val employeeRepository: EmployeeRepository,
    private val managerRepository: ManagerRepository
) : ViewModel() {
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences


    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message


    //метод встановлення NavController, який викликається у фрагменті
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //метод встановлення SharedPreferences, який викликається у фрагменті
    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }

    private fun showInvalideMessage() {
        _message.value = "Невірний логін чи пароль!"
    }

    private fun showSuccessfulMessage(name: String, surname: String){
        _message.value = "Вітаємо, $name $surname"
    }

    fun loginCustomer(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.loginCustomer(email, password)
            withContext(Dispatchers.Main) {
                result.onSuccess { customer ->
                    sharedPreferences.edit().putInt("customerId", customer.id).apply()
                    showSuccessfulMessage(customer.name, customer.surname)
                    navController.navigate(R.id.action_loginFragment_to_customerMainPageFragment)
                }.onFailure {
                    showInvalideMessage()
                }
            }
        }
    }

    fun loginEmployee(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = employeeRepository.loginEmployee(email, password)
            withContext(Dispatchers.Main) {
                result.onSuccess { employee ->
                    sharedPreferences.edit().putInt("employeeId", employee.id).apply()
                    showSuccessfulMessage(employee.name, employee.surname)
                    navController.navigate(R.id.action_loginFragment_to_employeeMainPageFragment)
                }.onFailure {
                    showInvalideMessage()
                }
            }
        }
    }

    fun loginManager(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = managerRepository.loginManager(email, password)
            withContext(Dispatchers.Main) {
                result.onSuccess { manager ->
                    sharedPreferences.edit().putInt("managerId", manager.id).apply()
                    showSuccessfulMessage(manager.name, manager.surname)
                    navController.navigate(R.id.action_loginFragment_to_managerMainPageFragment)
                }.onFailure {
                    showInvalideMessage()
                }
            }
        }
    }
}
