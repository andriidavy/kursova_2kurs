package com.example.registration.retrofit.employeeApi

import com.example.registration.model.users.Employee
import retrofit2.http.GET

interface EmployeeApi {
    @GET("/employee/get-all")
    suspend fun getEmployeeAll(): List<Employee>;
}