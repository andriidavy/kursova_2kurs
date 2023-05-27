package com.example.registration.repository

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.Customer
import com.example.registration.model.users.Employee
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.retrofit.employeeApi.EmployeeApi
import retrofit2.Call
import retrofit2.http.Query

class EmployeeRepository(private val employeeApi: EmployeeApi) {

    suspend fun getEmployees(): List<Employee> {
        return employeeApi.getEmployeeAll()
    }

    suspend fun loginEmployee(email: String, password: String): Result<Employee> {
        return try {
            val employee = employeeApi.loginEmployee(email, password)
            Result.success(employee)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getEmployeeProfile(employeeId: Int) : EmployeeProfileDTO{
        return employeeApi.getEmployeeProfile(employeeId)
    }

    suspend fun getProcessingCustomsForEmployee(employeeId: Int): List<CustomDTO>{
        return employeeApi.getProcessingCustomsForEmployee(employeeId)
    }

    suspend fun getProcessedCustomsForEmployee(employeeId: Int): List<CustomDTO> {
        return employeeApi.getProcessedCustomsForEmployee(employeeId)
    }

    suspend fun getAllAcceptedReportsForEmployee(employeeId: Int): List<ReportDTO>{
        return employeeApi.getAllAcceptedReportsForEmployee(employeeId)
    }

    suspend fun getAllWaitingReportsForEmployee(employeeId: Int): List<ReportDTO>{
        return employeeApi.getAllWaitingReportsForEmployee(employeeId)
    }

    suspend fun getAllRejectedReportsForEmployee(employeeId: Int) : List<ReportDTO>{
        return employeeApi.getAllRejectedReportsForEmployee(employeeId)
    }

    suspend fun createReport(
        employeeId: Int,
        customId: Int,
        reportText: String
    ){
        return employeeApi.createReport(employeeId,customId, reportText)
    }

    suspend fun setCustomSent(customId: Int){
        return employeeApi.setCustomSent(customId)
    }
}