package com.example.registration.retrofit.managerApi

import com.example.registration.model.users.Manager
import retrofit2.http.GET

interface ManagerApi {
        @GET("/manager/get-all")
    suspend fun getManagersAll() : List<Manager>;
}