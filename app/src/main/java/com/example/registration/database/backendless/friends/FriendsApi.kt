package com.example.registration.database.backendless.friends

import com.example.registration.model.friends.FriendItem
import com.example.registration.model.friends.FriendsDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface FriendsApi {
    @GET("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Friends")
    suspend fun getAcceptedFriendsList(
        @Query("where") where: String
    ): List<FriendItem>
    @GET("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Users")
    suspend fun getAcceptedFriendsListInfo(
        @Query("where") where: String
    ): List<FriendsDTO>
    @DELETE("https://api.backendless.com/FF1A5A1D-9D50-49DE-FF52-3A572F090300/2240799C-8598-4FE5-A987-E63812C61590/data/Friends/{idFriends}")
    suspend fun deleteFriend(
        @Header("user-token") userToken: String,
        @Path("idFriends") idPlace: String
    )

}