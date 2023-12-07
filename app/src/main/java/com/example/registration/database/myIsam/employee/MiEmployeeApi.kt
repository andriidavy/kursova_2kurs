package com.example.registration.database.myIsam.employee

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MiEmployeeApi {
    @GET("/mi/employee/login")
    suspend fun loginEmployee(@Query("email") email:String, @Query("password") password: String) : Int

    @GET("/mi/employee/get-employee-by-id")
    suspend fun getEmployeeProfile(@Query("employeeId") employeeId: Int) : EmployeeProfileDTO

    @GET("/mi/employee/custom/get-in-processing")
    suspend fun getProcessingCustomsForEmployee(@Query("employeeId") employeeId: Int): List<CustomDTO>

    @GET("/mi/employee/custom/get-processed")
    suspend fun getProcessedCustomsForEmployee(@Query("employeeId") employeeId: Int): List<CustomDTO>

    @GET("/mi/employee/get-accepted-reports-for-employee")
    suspend fun getAllAcceptedReportsForEmployee(@Query("employeeId") employeeId: Int): List<ReportDTO>

    @GET("/mi/employee/get-waiting-reports-for-employee")
    suspend fun getAllWaitingReportsForEmployee(@Query("employeeId") employeeId: Int): List<ReportDTO>

    @GET("/mi/employee/get-rejected-reports-for-employee")
    suspend fun getAllRejectedReportsForEmployee(@Query("employeeId") employeeId: Int) : List<ReportDTO>

    @POST("/mi/employee/custom/provide-report")
    suspend fun createReport(
        @Query("employeeId") employeeId: Int,
        @Query("customId") customId: Int,
        @Query("reportText") reportText: String
    )

    @POST("/mi/employee/custom/set-sent-status")
    suspend fun setCustomSent(@Query("customId") customId: Int)
}