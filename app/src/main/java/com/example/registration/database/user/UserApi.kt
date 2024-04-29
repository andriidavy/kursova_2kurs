package com.example.registration.database.user

import com.example.registration.model.users.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("users/register")
    suspend fun registerUser(@Body user: User): User

}
