package com.example.registration.retrofit.managerApi

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.Manager
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ManagerApi {
    @GET("/manager/get-all")
    suspend fun getManagersAll(): List<Manager>

    @GET("/manager/custom/get-created")
    suspend fun getAllCreatedCustoms(): List<CustomDTO>

    @GET("/manager/custom/get-waiting")
    suspend fun  getAllWaiting(): List<ReportDTO>

    @GET("/manager/employee/profile/get-all")
    suspend fun getAllEmployeesProfile(): List<EmployeeProfileDTO>

    @POST("/manager/custom/assign-employee/")
    suspend fun assignEmployeeToCustom(
        @Query("customId") customId: Int,
        @Query("employeeId") employeeId: Int
    )

    @POST("/manager/custom/report/accept")
    suspend fun setReportAccepted(@Query("reportId") reportId: Int)

    @POST("/manager/custom/report/reject")
    suspend fun setReportRejected(@Query("reportId") reportId: Int)
}