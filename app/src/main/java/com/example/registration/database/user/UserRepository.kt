package com.example.registration.database.user

import com.example.registration.model.users.User
import com.example.registration.model.users.User.Gender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    fun registerUser(name: String, email: String, password: String, nationality: String, age: Int, gender: Gender): Flow<Result<User>> = flow {
        emit(
            try {
                val user =
                    userApi.registerUser(User(name, email, password, nationality, age, gender))
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }
}