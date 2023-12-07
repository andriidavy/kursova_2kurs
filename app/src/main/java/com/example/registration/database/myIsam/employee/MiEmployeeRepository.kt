package com.example.registration.database.myIsam.employee

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MiEmployeeRepository @Inject constructor(private val miEmployeeApi: MiEmployeeApi) {

    fun loginEmployee(email: String, password: String): Flow<Result<Int>> = flow {
        emit(
            try {
                val employee = miEmployeeApi.loginEmployee(email, password)
                Result.success(employee)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getEmployeeProfile(employeeId: Int): Flow<EmployeeProfileDTO> = flow {
        emit(miEmployeeApi.getEmployeeProfile(employeeId))
    }

    fun getProcessingCustomsForEmployee(employeeId: Int): Flow<List<CustomDTO>> = flow {
        emit(miEmployeeApi.getProcessingCustomsForEmployee(employeeId))
    }

    fun getProcessedCustomsForEmployee(employeeId: Int): Flow<List<CustomDTO>> = flow {
        emit(miEmployeeApi.getProcessedCustomsForEmployee(employeeId))
    }

    fun getAllAcceptedReportsForEmployee(employeeId: Int): Flow<List<ReportDTO>> = flow {
        emit(miEmployeeApi.getAllAcceptedReportsForEmployee(employeeId))
    }

    fun getAllWaitingReportsForEmployee(employeeId: Int): Flow<List<ReportDTO>> = flow {
        emit(miEmployeeApi.getAllWaitingReportsForEmployee(employeeId))
    }

    fun getAllRejectedReportsForEmployee(employeeId: Int): Flow<List<ReportDTO>> = flow {
        emit(miEmployeeApi.getAllRejectedReportsForEmployee(employeeId))
    }

    fun createReport(
        employeeId: Int,
        customId: Int,
        reportText: String
    ): Flow<Result<Unit>> = flow {
        emit(
            try {
                val result = miEmployeeApi.createReport(employeeId, customId, reportText)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun setCustomSent(customId: Int) {
        return miEmployeeApi.setCustomSent(customId)
    }
}