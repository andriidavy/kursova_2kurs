package com.example.registration.retrofit.employeeApi

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.users.Employee
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EmployeeApi {
    @GET("/employee/get-all")
    suspend fun getEmployeeAll(): List<Employee>

    @GET("/employee/custom/get-in-processing")
    suspend fun getProcessingCustomsForEmployee(@Query("employeeId") employeeId: Int): List<CustomDTO>

    @POST("/employee/custom/create-report")
    suspend fun createReport(
        @Query("employeeId") employeeId: Int,
        @Query("customId") customId: Int,
        @Query("reportText") reportText: String
    )
}