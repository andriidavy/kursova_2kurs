package com.example.registration.repository

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.users.Customer
import com.example.registration.model.users.Employee
import com.example.registration.retrofit.employeeApi.EmployeeApi
import retrofit2.Call
import retrofit2.http.Query

class EmployeeRepository(private val employeeApi: EmployeeApi) {

    suspend fun getEmployees(): List<Employee> {
        return employeeApi.getEmployeeAll()
    }

    suspend fun getProcessingCustomsForEmployee(employeeId: Int): List<CustomDTO>{
        return employeeApi.getProcessingCustomsForEmployee(employeeId)
    }

    suspend fun createReport(
        employeeId: Int,
        customId: Int,
        reportText: String
    ){
        return employeeApi.createReport(employeeId,customId, reportText)
    }

}