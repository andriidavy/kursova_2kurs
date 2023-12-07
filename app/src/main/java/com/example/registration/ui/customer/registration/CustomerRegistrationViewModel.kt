package com.example.registration.ui.customer.registration

import androidx.lifecycle.ViewModel
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.myIsam.customer.MiCustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CustomerRegistrationViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val miCustomerRepository: MiCustomerRepository
) : ViewModel() {

    fun insertCustomer(
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> {
        return customerRepository.insertCustomer(name, surname, email, password, repPassword)
    }

    fun miInsertCustomer(
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> {
        return miCustomerRepository.insertCustomer(name, surname, email, password, repPassword)
    }
}

