package com.example.registration.ui.login

import com.example.registration.database.customer.CustomerRepository
import androidx.lifecycle.*
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
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private val _userId = MutableLiveData<Int>()
    val userId: LiveData<Int>
        get() = _userId

    fun login(email: String, password: String, num: Int) : LiveData<Boolean> {
        val loginResult = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val result = when (num) {
                0 -> customerRepository.loginCustomer(email, password)
                1 -> employeeRepository.loginEmployee(email, password)
                2 -> managerRepository.loginManager(email, password)
                else -> null
            }

            withContext(Dispatchers.Main) {
                result?.onSuccess { user ->
                    //set userId to datastore
                    _userId.value = user.id
                    _message.value = "Вітаємо, ${user.name} ${user.surname}"

                    loginResult.value = true
                }?.onFailure {
                    _message.value = "Невірний логін чи пароль!"

                    loginResult.value = false
                }
            }
        }
        return loginResult
    }
}

