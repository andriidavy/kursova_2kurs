package com.example.registration.repository

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.Manager
import com.example.registration.retrofit.managerApi.ManagerApi
import retrofit2.Call
import retrofit2.http.Query

class ManagerRepository(private val managerApi: ManagerApi) {

    suspend fun getManagers(): List<Manager> {
        return managerApi.getManagersAll()
    }

    suspend fun getAllCreatedCustoms(): List<CustomDTO> {
        return managerApi.getAllCreatedCustoms()
    }

    suspend fun  getAllEmployeesProfile(): List<EmployeeProfileDTO>{
        return managerApi.getAllEmployeesProfile()
    }

    suspend fun assignEmployeeToCustom(customId: Int, employeeId: Int){
        managerApi.assignEmployeeToCustom(customId, employeeId)
    }
}