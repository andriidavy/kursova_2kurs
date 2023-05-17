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

class LoginViewModel(
    private val customerRepository: CustomerRepository,
    private val employeeRepository: EmployeeRepository,
    private val managerRepository: ManagerRepository
) : ViewModel() {
    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences

//FOR ONE WAY BINDING(ENCAPSULATION)
//    private val _inputEmail = MutableLiveData<String>()
//    val inputEmail : LiveData<String>
//    get() = _inputEmail
//
//    private val _inputPassword = MutableLiveData<String>()
//    val inputPassword: LiveData<String>
//    get() = _inputPassword

    //FOR TWO WAY BINDING (NON ENCAPSULATION)
    val inputEmail = MutableLiveData<String?>()
    val inputPassword = MutableLiveData<String?>()

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private var customers: List<Customer>? = null
    private var employees: List<Employee>? = null
    private var managers: List<Manager>? = null

    //метод встановлення NavController, який викликається у фрагменті
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //метод встановлення SharedPreferences, який викликається у фрагменті
    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }


//    //CUSTOMER LOGIN METHODS

    private fun callGetAllCustomers() {
        viewModelScope.launch(Dispatchers.IO) {
        customers = customerRepository.getCustomers()
        }
    }

    fun loginCustomer() {
        callGetAllCustomers()
        val email = inputEmail.value
        val password = inputPassword.value

        customers?.let { customersList ->
            for (i in customersList.indices) {
                if (email == customersList[i].email && password == customersList[i].password) {
                    sharedPreferences.edit().putInt("customerId", customersList[i].id).apply()
                    navController.navigate(R.id.action_loginFragment_to_customerMainPageFragment)
                    _message.value = "Вітаємо ${customersList[i].name} ${customersList[i].surname}"
                    return
                } else {
                    _message.value = "Помилка входу, перевірте вхідні дані"
                    inputEmail.value = ""
                    inputPassword.value = ""
                }
            }
        } ?: run {
            _message.value = "Empty database!"
        }
    }

//EMPLOYEE LOGIN METHODS

    private fun callGetAllEmployees() {
        viewModelScope.launch(Dispatchers.IO) {
            employees = employeeRepository.getEmployees()
        }
    }

    fun loginEmployee() {
        callGetAllEmployees()
        val email = inputEmail.value
        val password = inputPassword.value

        employees?.let { employeesList ->
            for (i in employeesList.indices) {
                if (email == employeesList[i].email && password == employeesList[i].password) {
                    sharedPreferences.edit().putInt("employeeId", employeesList[i].id).apply()
                    navController.navigate(R.id.action_loginFragment_to_employeeMainPageFragment)
                    _message.value = "Вітаємо ${employeesList[i].name} ${employeesList[i].surname}"
                    return
                } else {
                    _message.value = "Помилка входу, перевірте вхідні дані"
                    inputEmail.value = ""
                    inputPassword.value = ""
                }
            }
        } ?: run {
            _message.value = "Empty database!"
        }
    }

//MANAGER LOGIN METHODS

    private fun callGetAllManagers() {
        viewModelScope.launch(Dispatchers.IO) {
            managers = managerRepository.getManagers()
        }
    }

    fun loginManager() {
        callGetAllManagers()
        val email = inputEmail.value
        val password = inputPassword.value

        managers?.let { managersList ->
            for (i in managersList.indices) {
                if (email == managersList[i].email && password == managersList[i].password) {
                    sharedPreferences.edit().putInt("managerId", managersList[i].id).apply()
                    navController.navigate(R.id.action_loginFragment_to_managerMainPageFragment)
                    _message.value = "Вітаємо ${managersList[i].name} ${managersList[i].surname}"
                    return
                } else {
                    _message.value = "Помилка входу, перевірте вхідні дані"
                    inputEmail.value = ""
                    inputPassword.value = ""
                }
            }
        } ?: run {
            _message.value = "Empty database!"
        }
    }
}
