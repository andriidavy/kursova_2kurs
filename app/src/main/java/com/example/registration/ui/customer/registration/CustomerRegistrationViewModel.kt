package com.example.registration.ui.customer.registration

import com.example.registration.database.customer.CustomerRepository
import androidx.lifecycle.*
import com.example.registration.model.users.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class CustomerRegistrationViewModel @Inject constructor(private val customerRepository: CustomerRepository) :
    ViewModel() {

    private val _message = MutableLiveData<String>()
    val message: LiveData<String>
        get() = _message

    fun insertCustomer(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Flow<Result<Customer>> = flow {
        emit(customerRepository.save(name, surname, email, password))
    }.flowOn(Dispatchers.IO)

    fun showSuccessfulMessage() {
        _message.value = "Реєстрація пройшла успішно!"
    }

    fun showInvalidMessage() {
        _message.value = "Покупець з таким email вже існує!"
    }
}

