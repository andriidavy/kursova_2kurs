package com.example.registration.ui.registration

import androidx.lifecycle.ViewModel
import com.example.registration.database.backendless.user.UserRepository
import com.example.registration.model.users.User
import com.example.registration.model.users.User.Gender
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun registerUser(name: String, email: String, password: String, nationality: String, age: Int, gender: Gender): Flow<Result<User>> {
        return userRepository.registerUser(name, email, password, nationality, age, gender)
    }
}

