package com.example.registration.repository

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.Manager
import com.example.registration.retrofit.managerApi.ManagerApi
import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

class ManagerRepository(private val managerApi: ManagerApi) {

    suspend fun getManagers(): List<Manager> {
        return managerApi.getManagersAll()
    }

    suspend fun getAllCreatedCustoms(): List<CustomDTO> {
        return managerApi.getAllCreatedCustoms()
    }

    suspend fun  getAllWaiting(): List<ReportDTO>{
        return managerApi.getAllWaiting()
    }

    suspend fun  getAllEmployeesProfile(): List<EmployeeProfileDTO>{
        return managerApi.getAllEmployeesProfile()
    }

    suspend fun assignEmployeeToCustom(customId: Int, employeeId: Int){
        managerApi.assignEmployeeToCustom(customId, employeeId)
    }

    suspend fun setReportAccepted(reportId: Int){
        return managerApi.setReportAccepted(reportId)
    }

    suspend fun setReportRejected(reportId: Int){
        return managerApi.setReportRejected(reportId)
    }
}