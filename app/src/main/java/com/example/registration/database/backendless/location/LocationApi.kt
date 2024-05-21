package com.example.registration.database.backendless.location

import com.example.registration.model.places.AddingPlaceDTO
import com.example.registration.model.places.PlaceItem
import com.example.registration.model.places.likes.AddLikeForPlaceData
import com.example.registration.model.places.likes.LikeItem
import com.example.registration.model.users.data.UserLocationDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
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

    @GET("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Place")
    suspend fun getAllPlaces(): List<PlaceItem>

    @GET("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Place")
    suspend fun getPlacesBySearchLine(
        @Query("where") where: String
    ): List<PlaceItem>

    @DELETE("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Place/{idPlace}")
    suspend fun deletePlace(
        @Header("user-token") userToken: String,
        @Path("idPlace") idPlace: String
    )

    @POST("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/LikesForPlace")
    @Headers("Content-Type: application/json")
    suspend fun addLikeForPlace(
        @Header("user-token") userToken: String,
        @Body place: AddLikeForPlaceData
    ): AddLikeForPlaceData

    @DELETE("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/LikesForPlace/{idLike}")
    suspend fun deleteLikeForPlace(
        @Header("user-token") userToken: String,
        @Path("idLike") idPlace: String
    )

    @GET("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/LikesForPlace")
    suspend fun getLikesForPlace(): List<LikeItem>
}