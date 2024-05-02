package com.example.registration.database.backendless.user

import com.example.registration.model.users.User
import com.example.registration.model.users.data.LoginRequest
import com.example.registration.model.users.data.UserDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @POST("users/register")
    suspend fun registerUser(@Body user: User): User

    @POST("files/{folderName}/sharedWithMe")
    suspend fun createFolder(
        @Path("folderName") folderName: String
    )
    @POST("users/login")
    @Headers("Content-Type: application/json")
    suspend fun loginUser(@Body loginRequest: LoginRequest): UserDTO

    @GET("users/isvalidusertoken/{userToken}")
    suspend fun isTokenValid(
        @Path("userToken") userToken: String
    ): Boolean

    @GET("users/restorepassword/{userIdentity}")
    suspend fun restorePassword(
        @Path("userIdentity") userIdentity: String
    )

    @GET("data/Users/{objectId}")
    @Headers("user-token: {userToken}")
    suspend fun getUser(
        @Path("objectId") objectId: String,
        @Header("user-token") userToken: String
    ): UserDTO

    @GET("users/logout")
    suspend fun logoutUser(@Query("user_token") token: String)
}
