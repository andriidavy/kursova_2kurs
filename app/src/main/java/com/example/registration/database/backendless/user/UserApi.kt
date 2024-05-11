package com.example.registration.database.backendless.user

import com.example.registration.model.users.User
import com.example.registration.model.users.data.GuestUserDTO
import com.example.registration.model.users.data.ImageDTO
import com.example.registration.model.users.data.LoginRequest
import com.example.registration.model.users.data.UserDTO
import com.example.registration.model.users.data.UserLocationDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @POST("users/register")
    suspend fun registerUser(@Body user: User): User

    @POST("files/{folderName}/sharedWithMe")
    suspend fun createStartUserFolder(
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
    suspend fun getUser(
        @Path("objectId") objectId: String,
        @Header("user-token") userToken: String
    ): UserDTO

    @GET("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Users")
    suspend fun getUserByName(
        @Query("where") where: String,
        @Query("property") property: String
    ): List<GuestUserDTO>

    @PUT("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Users")
    @Headers("Content-Type: application/json")
    suspend fun updateUser(
        @Header("user-token") userToken: String,
        @Body updateRequest: UserDTO
    ): UserDTO

    @PUT("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Users")
    @Headers("Content-Type: application/json")
    suspend fun updateUserProfileImage(
        @Header("user-token") userToken: String,
        @Body updateRequest: ImageDTO
    ): ImageDTO

    @PUT("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Users")
    @Headers("Content-Type: application/json")
    suspend fun updateUserLocation(
        @Header("user-token") userToken: String,
        @Body updateRequest: UserLocationDTO
    ): UserLocationDTO

    @GET("users/logout")
    suspend fun logoutUser(@Header("user-token") userToken: String)
}
