package com.example.registration.database.employee

import com.example.registration.global.LoginResponse
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface EmployeeApi {
    @GET("/auth/employee/login")
    suspend fun loginEmployee(
        @Query("email") email: String,
        @Query("password") password: String
    ): String

    @GET("/auth/employee/get-employee-by-id")
    suspend fun getEmployeeProfile(
        @Header("Authorization") token: String,
        @Query("employeeId") employeeId: Int
    ): EmployeeProfileDTO

    @GET("/order-processing/employee/custom/get-in-processing")
    suspend fun getProcessingCustomsForEmployee(
        @Header("Authorization") token: String,
        @Query("employeeId") employeeId: Int
    ): List<CustomDTO>

    @GET("/order-processing/employee/custom/get-processed")
    suspend fun getProcessedCustomsForEmployee(
        @Header("Authorization") token: String,
        @Query("employeeId") employeeId: Int
    ): List<CustomDTO>

    @GET("/order-processing/employee/get-accepted-reports-for-employee")
    suspend fun getAllAcceptedReportsForEmployee(
        @Header("Authorization") token: String,
        @Query("employeeId") employeeId: Int
    ): List<ReportDTO>

    @GET("/order-processing/employee/get-waiting-reports-for-employee")
    suspend fun getAllWaitingReportsForEmployee(
        @Header("Authorization") token: String,
        @Query("employeeId") employeeId: Int
    ): List<ReportDTO>

    @GET("/order-processing/employee/get-rejected-reports-for-employee")
    suspend fun getAllRejectedReportsForEmployee(
        @Header("Authorization") token: String,
        @Query("employeeId") employeeId: Int
    ): List<ReportDTO>

    @POST("/order-processing/employee/custom/provide-report")
    suspend fun createReport(
        @Header("Authorization") token: String,
        @Query("employeeId") employeeId: Int,
        @Query("customId") customId: Int,
        @Query("reportText") reportText: String
    )

    @POST("/order-processing/employee/custom/set-sent-status")
    suspend fun setCustomSent(
        @Header("Authorization") token: String,
        @Query("customId") customId: Int
    )
}