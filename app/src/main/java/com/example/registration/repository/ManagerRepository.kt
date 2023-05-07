package com.example.registration.repository

import com.example.registration.model.users.Manager
import com.example.registration.retrofit.managerApi.ManagerApi
import retrofit2.Call

class ManagerRepository(private val managerApi: ManagerApi) {

    fun getManagers(): Call<MutableList<Manager>> {
        return managerApi.managersAll
    }
}