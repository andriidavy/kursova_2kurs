package com.example.registration.UI.login

import android.content.SharedPreferences
import com.example.registration.database.customer.CustomerRepository
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.registration.R
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.manager.ManagerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val employeeRepository: EmployeeRepository,
    private val managerRepository: ManagerRepository
) : ViewModel() {
    private lateinit var navController: NavController


    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId


    //метод встановлення NavController, який викликається у фрагменті
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    fun login(email: String, password: String, num: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (num) {
                0 -> customerRepository.loginCustomer(email, password)
                1 -> employeeRepository.loginEmployee(email, password)
                2 -> managerRepository.loginManager(email, password)
                else -> null
            }

            withContext(Dispatchers.Main) {
                result?.onSuccess { user ->
                    _userId.value = user.id
                    _message.value = "Вітаємо, ${user.name} ${user.surname}"
                    when (num) {
                        0 -> navController.navigate(R.id.action_loginFragment_to_customerMainPageFragment)
                        1 -> navController.navigate(R.id.action_loginFragment_to_employeeMainPageFragment)
                        2 -> navController.navigate(R.id.action_loginFragment_to_managerMainPageFragment)
                    }
                }?.onFailure {
                    _message.value = "Невірний логін чи пароль!"
                }
            }
        }
    }

}

