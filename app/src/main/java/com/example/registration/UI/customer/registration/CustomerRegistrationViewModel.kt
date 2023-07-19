package com.example.registration.UI.customer.registration

import com.example.registration.database.customer.CustomerRepository
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CustomerRegistrationViewModel @Inject constructor(val customerRepository: CustomerRepository) :
    ViewModel() {
    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    private fun showInvalidMessage() {
        _message.value = "Покупець з таким email вже існує!"
    }

    private fun showSuccessfulMessage() {
        _message.value = "Реєстрація пройшла успішно!"
    }

    fun insertCustomer(
        name: String,
        surname: String,
        email: String,
        password: String
    ): MutableLiveData<Boolean> {
        val insertResult = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val result = customerRepository.save(name, surname, email, password)
            withContext(Dispatchers.Main) {
                result.onSuccess {
                    showSuccessfulMessage()
                    insertResult.value = true
                }.onFailure {
                    showInvalidMessage()
                    insertResult.value = false
                }
            }
        }
        return insertResult
    }
}

