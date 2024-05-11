package com.example.registration.database.backendless.location

import com.example.registration.model.places.AddingPlaceDTO
import com.example.registration.model.users.data.UserLocationDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LocationApi {
    @POST("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Place")
    @Headers("Content-Type: application/json")
    suspend fun addPlace(
        @Header("user-token") userToken: String,
        @Body place: AddingPlaceDTO
    ): AddingPlaceDTO

    @GET("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Users")
    suspend fun getMyLocation(
        @Query("where") where: String
    ): List<UserLocationDTO>
}