package com.example.registration.database.employee

import com.example.registration.global.LoginResponse
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EmployeeRepository @Inject constructor(private val employeeApi: EmployeeApi) {

    fun loginEmployee(email: String, password: String): Flow<Result<String>> = flow {
        emit(
            try {
                val employee = employeeApi.loginEmployee(email, password)
                Result.success(employee)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getEmployeeProfile(token: String, employeeId: Int): Flow<EmployeeProfileDTO> = flow {
        emit(employeeApi.getEmployeeProfile(token, employeeId))
    }

    fun getProcessingCustomsForEmployee(token: String, employeeId: Int): Flow<List<CustomDTO>> = flow {
        emit(employeeApi.getProcessingCustomsForEmployee(token, employeeId))
    }

    fun getProcessedCustomsForEmployee(token: String, employeeId: Int): Flow<List<CustomDTO>> = flow {
        emit(employeeApi.getProcessedCustomsForEmployee(token, employeeId))
    }

    fun getAllAcceptedReportsForEmployee(token: String, employeeId: Int): Flow<List<ReportDTO>> = flow {
        emit(employeeApi.getAllAcceptedReportsForEmployee(token, employeeId))
    }

    fun getAllWaitingReportsForEmployee(token: String, employeeId: Int): Flow<List<ReportDTO>> = flow {
        emit(employeeApi.getAllWaitingReportsForEmployee(token, employeeId))
    }

    fun getAllRejectedReportsForEmployee(token: String, employeeId: Int): Flow<List<ReportDTO>> = flow {
        emit(employeeApi.getAllRejectedReportsForEmployee(token, employeeId))
    }

    fun createReport(
        token: String,
        employeeId: Int,
        customId: Int,
        reportText: String
    ): Flow<Result<Unit>> = flow {
        emit(
            try {
                val result = employeeApi.createReport(token, employeeId, customId, reportText)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun setCustomSent(token: String, customId: Int) {
        return employeeApi.setCustomSent(token, customId)
    }
}