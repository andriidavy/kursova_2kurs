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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Arrays.setAll

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

    private var customers: MutableList<Customer>? = null
    private var employees: MutableList<Employee>? = null
    private var managers: MutableList<Manager>? = null

    //метод встановлення NavController, який викликається у фрагменті
    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    //метод встановлення SharedPreferences, який викликається у фрагменті
    fun setSharedPreferences(sharedPreferences: SharedPreferences) {
        this.sharedPreferences = sharedPreferences
    }


    //CUSTOMER LOGIN METHODS
    private fun callGetAllCustomers() = customerRepository.getCustomers().enqueue(object :
        Callback<MutableList<Customer>> {
        override fun onResponse(
            call: Call<MutableList<Customer>>,
            response: Response<MutableList<Customer>>
        ) {
            setAllCustomers(response.body())
        }

        override fun onFailure(call: Call<MutableList<Customer>>, t: Throwable) {
            _message.value = "Failure call getCustomers"
        }
    })

    private fun setAllCustomers(body: MutableList<Customer>?) {
        customers = body
    }

    fun loginCustomer() {
        callGetAllCustomers()
        val email = inputEmail.value
        val password = inputPassword.value

        customers?.let { customersList ->
            for (i in customersList.indices) {
                if (email == customersList[i].email && password == customersList[i].password) {
                    val customerId = customersList[i].id
                    sharedPreferences.edit().putInt("customerId", customerId)
                    sharedPreferences.edit().apply()
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

    private fun callGetAllEmployees() = employeeRepository.getEmployees().enqueue(object :
        Callback<MutableList<Employee>> {
        override fun onResponse(
            call: Call<MutableList<Employee>>,
            response: Response<MutableList<Employee>>
        ) {
            setAllEmployees(response.body())
        }

        override fun onFailure(call: Call<MutableList<Employee>>, t: Throwable) {
            _message.value = "Failure call getEmployees"
        }
    })

    private fun setAllEmployees(body: MutableList<Employee>?) {
        employees = body
    }

    fun loginEmployee() {
        callGetAllEmployees()
        val email = inputEmail.value
        val password = inputPassword.value

        employees?.let { employeesList ->
            for (i in employeesList.indices) {
                if (email == employeesList[i].email && password == employeesList[i].password) {
                    val employeeId = employeesList[i].id
                    sharedPreferences.edit().putInt("employeeId", employeeId)
                    sharedPreferences.edit().apply()
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

    private fun callGetAllManagers() = managerRepository.getManagers().enqueue(object :
        Callback<MutableList<Manager>> {
        override fun onResponse(
            call: Call<MutableList<Manager>>,
            response: Response<MutableList<Manager>>
        ) {
            setAllManagers(response.body())
        }

        override fun onFailure(call: Call<MutableList<Manager>>, t: Throwable) {
            _message.value = "Failure call getManagers"
        }
    })

    private fun setAllManagers(body: MutableList<Manager>?) {
        managers = body
    }

    fun loginManager() {
        callGetAllManagers()
        val email = inputEmail.value
        val password = inputPassword.value

        managers?.let { managersList ->
            for (i in managersList.indices) {
                if (email == managersList[i].email && password == managersList[i].password) {
                    val managerId = managersList[i].id
                    sharedPreferences.edit().putInt("managerId", managerId)
                    sharedPreferences.edit().apply()
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


//REALIZATION FOR ROOM DATABASE
//    private val _customers = MutableStateFlow<List<Customer>>(emptyList())
//    val customers: StateFlow<List<Customer>> = _customers
//
//    fun doGetAllCustomers() {
//        viewModelScope.launch(Dispatchers.IO) {
//            repository.customers.collect {
//                _customers.value = it
//            }
//        }
//    }
//
//    fun check() {
//        doGetAllCustomers()
//        customers.value?.let { customersList ->
//            if (customersList.isNotEmpty()) {
//                _message.value = "База даних НЕ порожня"
//            } else {
//                _message.value = "База даних порожня1"
//            }
//        } ?: run {
//            _message.value = "База даних порожня2"
//        }
//    }
//    fun login() {
//    doGetAllCustomers()
//        val email = inputEmail.value
//        val password = inputPassword.value
//        customers.value?.let { customersList ->
//            for (i in customersList.indices) {
//                    if (email == customersList[i].email && password == customersList[i].password) {
//                        navController.navigate(R.id.action_loginFragment_to_customerMainPageFragment)
//                        _message.value = "Вітаємо ${customersList[i].name} ${customersList[i].surname}"
//                        return
//                    }
//                else{
//                        _message.value = "Помилка входу, перевірте вхідні дані"
//                        inputEmail.value = ""
//                        inputPassword.value = ""
//                }
//            }
//        }
