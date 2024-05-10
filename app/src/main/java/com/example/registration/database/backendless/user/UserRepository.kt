package com.example.registration.database.backendless.user

import android.util.Log
import com.example.registration.model.users.User
import com.example.registration.model.users.User.Gender
import com.example.registration.model.users.data.GuestUserDTO
import com.example.registration.model.users.data.ImageDTO
import com.example.registration.model.users.data.LoginRequest
import com.example.registration.model.users.data.UserDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    fun registerUser(
        name: String,
        email: String,
        password: String,
        nationality: String,
        age: Int,
        gender: Gender
    ): Flow<Result<User>> = flow {
        emit(
            try {
                val user =
                    userApi.registerUser(User(name, email, password, nationality, age, gender))
                userApi.createStartUserFolder(name)
                Result.success(user)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun loginUser(email: String, password: String): Flow<Result<UserDTO>> = flow {
        try {
            val loginResponse = userApi.loginUser(LoginRequest(email, password))
            Log.d("UserRepository", "Login successful: $loginResponse")
            emit(Result.success(loginResponse))
        } catch (e: Exception) {
            Log.e("UserRepository", "Login failed", e)
            emit(Result.failure(e))
        }
    }

    suspend fun restorePassword(email: String) {
        userApi.restorePassword(email)
    }

    fun getUser(objectId: String, token: String): Flow<Result<UserDTO>> = flow {
        try {
            val getUser = userApi.getUser(objectId, token)
            Log.d("UserRepository", "Get user successful: $getUser")
            emit(Result.success(getUser))
        } catch (e: Exception) {
            Log.e("UserRepository", "Get user failed", e)
            emit(Result.failure(e))
        }
    }

    fun logoutUser(token: String): Flow<Result<Unit>> = flow {
        try {
            userApi.logoutUser(token)
            Log.d("UserRepository", "Logout successful")
            emit(Result.success(Unit))
        } catch (e: Exception) {
            Log.e("UserRepository", "Logout failed", e)
            emit(Result.failure(e))
        }
    }

    suspend fun isTokenValid(token: String): Boolean {
        return userApi.isTokenValid(token)
    }

    fun getUserByName(userName: String): Flow<Result<List<GuestUserDTO>>> = flow {
        try {
            val whereClause = "name = '$userName'"
            val property = "name"
            val getUser = userApi.getUserByName(whereClause, property)
            Log.e("getUser", "user: $getUser")
            emit(Result.success(getUser))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun updateUser(userToken: String, updateRequest: UserDTO): Flow<Result<UserDTO>> = flow {
        try {
            val updatedUser = userApi.updateUser(userToken, updateRequest)
            Log.e("updateUser", "user: $updatedUser")
            emit(Result.success(updatedUser))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    fun updateUserProfileImage(userToken: String, imageDTO: ImageDTO): Flow<Result<ImageDTO>> = flow {
        try {
            val updatedImage = userApi.updateUserProfileImage(userToken, imageDTO)
            Log.e("updateUser", "user: $updatedImage")
            emit(Result.success(updatedImage))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}