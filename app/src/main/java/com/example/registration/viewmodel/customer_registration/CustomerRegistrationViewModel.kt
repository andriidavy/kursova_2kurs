package com.example.registration.viewmodel.customer_registration

import com.example.registration.repository.CustomerRepository
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.registration.R
import com.example.registration.model.users.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class CustomerRegistrationViewModel(val repository: CustomerRepository) : ViewModel() {
    private lateinit var navController: NavController

    val inputName = MutableLiveData<String>()
    val inputSurname = MutableLiveData<String>()
    val inputEmail = MutableLiveData<String>()
    val inputPassword = MutableLiveData<String>()

    var message = MutableLiveData<String>()
    fun registrationCustomer() {
        val name = inputName.value!!.toString()
        val surname = inputSurname.value!!.toString()
        val email = inputEmail.value!!.toString()
        val password = inputPassword.value!!.toString()
        insertCustomer(
            Customer(
                name,
                surname,
                email,
                password
            )
        )
        inputName.value = ""
        inputSurname.value = ""
        inputEmail.value = ""
        inputPassword.value = ""
//        message.value = "Реєстрація пройшла успішно"
        navController.navigate(R.id.action_registrationFragment_to_loginFragment)
    }

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    private fun insertCustomer(customer: Customer) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.save(customer)
        }
    }
}

