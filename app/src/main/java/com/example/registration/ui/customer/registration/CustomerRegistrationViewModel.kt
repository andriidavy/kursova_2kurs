package com.example.registration.ui.customer.registration

import androidx.lifecycle.ViewModel
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.model.users.Customer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CustomerRegistrationViewModel @Inject constructor(private val customerRepository: CustomerRepository) :
    ViewModel() {

    fun insertCustomer(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Flow<Result<Customer>> {
        return customerRepository.save(name, surname, email, password)
    }
}

