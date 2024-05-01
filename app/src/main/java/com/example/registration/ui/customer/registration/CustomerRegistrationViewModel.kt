package com.example.registration.ui.customer.registration

import androidx.lifecycle.ViewModel
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.model.users.User
import com.example.registration.model.users.User.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CustomerRegistrationViewModel @Inject constructor(
    private val customerRepository: CustomerRepository, private val userRepository: UserRepository
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

    fun registerUser(name: String, email: String, password: String, nationality: String, age: Int, gender: Gender): Flow<Result<User>> {
        return userRepository.registerUser(name, email, password, nationality, age, gender)
    }
}

