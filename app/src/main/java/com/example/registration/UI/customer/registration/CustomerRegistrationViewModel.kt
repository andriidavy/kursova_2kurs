package com.example.registration.UI.customer.registration

import com.example.registration.database.customer.CustomerRepository
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.example.registration.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerRegistrationViewModel @Inject constructor(val customerRepository: CustomerRepository) :
    ViewModel() {
    private lateinit var navController: NavController

    val message = MutableLiveData<String>()

    fun setNavController(navController: NavController) {
        this.navController = navController
    }

    private fun showInvalideMessage() {
        message.value = "Покупець з таким email вже існує!"
    }

    private fun showSuccessfulMessage() {
        message.value = "Реєстрація пройшла успішно!"
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

