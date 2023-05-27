package com.example.registration.viewmodel.customer_registration

import com.example.registration.repository.CustomerRepository
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.registration.R
import com.example.registration.model.users.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response

class CustomerRegistrationViewModel(val customerRepository: CustomerRepository) : ViewModel() {
    private lateinit var navController: NavController

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    private fun showInvalideMessage() {
        _message.value = "Покупець з таким email вже існує!"
    }
    private fun showSuccessfulMessage(){
        _message.value = "Реєстрація пройшла успішно!"
    }

    fun insertCustomer(name: String, surname: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.save(name, surname, email, password)
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    showSuccessfulMessage()
                    navController.navigate(R.id.action_registrationFragment_to_loginFragment)
                }.onFailure {
                    showInvalideMessage()
                }
            }
        }
    }
}

