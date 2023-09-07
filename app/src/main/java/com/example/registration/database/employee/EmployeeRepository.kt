package com.example.registration.database.employee

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.Employee
import com.example.registration.model.users.EmployeeProfileDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmployeeRepository @Inject constructor(private val employeeApi: EmployeeApi) {
    suspend fun loginEmployee(email: String, password: String): Result<Employee> {
        return try {
            val employee = employeeApi.loginEmployee(email, password)
            Result.success(employee)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getEmployeeProfile(employeeId: Int): Flow<EmployeeProfileDTO> = flow {
        emit(employeeApi.getEmployeeProfile(employeeId))
    }

    fun getProcessingCustomsForEmployee(employeeId: Int): Flow<List<CustomDTO>> = flow {
        emit(employeeApi.getProcessingCustomsForEmployee(employeeId))
    }

    suspend fun getProcessedCustomsForEmployee(employeeId: Int): List<CustomDTO> {
        return employeeApi.getProcessedCustomsForEmployee(employeeId)
    }

    suspend fun getAllAcceptedReportsForEmployee(employeeId: Int): List<ReportDTO> {
        return employeeApi.getAllAcceptedReportsForEmployee(employeeId)
    }

    suspend fun getAllWaitingReportsForEmployee(employeeId: Int): List<ReportDTO> {
        return employeeApi.getAllWaitingReportsForEmployee(employeeId)
    }

    suspend fun getAllRejectedReportsForEmployee(employeeId: Int): List<ReportDTO> {
        return employeeApi.getAllRejectedReportsForEmployee(employeeId)
    }

    fun createReport(
        employeeId: Int,
        customId: Int,
        reportText: String
    ): Flow<Result<Unit>> = flow {
        emit(
            try {
                val result = employeeApi.createReport(employeeId, customId, reportText)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun setCustomSent(customId: Int) {
        return employeeApi.setCustomSent(customId)
    }
}