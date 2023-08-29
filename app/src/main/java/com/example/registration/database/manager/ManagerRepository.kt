package com.example.registration.database.manager

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.product.Product
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.Employee
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.Manager
import com.example.registration.model.users.ManagerProfileDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ManagerRepository @Inject constructor(private val managerApi: ManagerApi) {
    suspend fun loginManager(email: String, password: String): Result<Manager> {
        return try {
            val manager = managerApi.loginManager(email, password)
            Result.success(manager)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllManagersProfileDTO(): List<ManagerProfileDTO> {
        return managerApi.getAllManagersProfileDTO()
    }

    suspend fun getManagerProfile(managerId: Int): ManagerProfileDTO {
        return managerApi.getManagerProfile(managerId)
    }

    suspend fun saveManager(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<Manager> {
        return try {
            val manager = managerApi.saveManager(name, surname, email, password)
            Result.success(manager)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteManagerById(managerId: Int) {
        return managerApi.deleteManagerById(managerId)
    }

    fun getAllCustoms(): Flow<List<CustomDTO>> = flow {
        emit(managerApi.getAllCustoms())
    }

    fun getAllProducts(): Flow<List<Product>> = flow {
        emit(managerApi.getAllProducts())
    }

    fun getAllCreatedCustoms(managerId: Int): Flow<List<CustomDTO>> = flow {
        emit(managerApi.getAllCreatedCustoms(managerId))
    }

    fun getAllWaiting(managerId: Int): Flow<List<ReportDTO>> = flow{
        emit(managerApi.getAllWaiting(managerId))
    }

    fun getAllEmployeesProfile(): Flow<List<EmployeeProfileDTO>> = flow {
        emit(managerApi.getAllEmployeesProfile())
    }

    suspend fun saveEmployee(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<Employee> {
        return try {
            val employee = managerApi.saveEmployee(name, surname, email, password)
            Result.success(employee)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteEmployeeById(employeeId: Int) {
        return managerApi.deleteEmployeeById(employeeId)
    }

    suspend fun assignEmployeeToCustom(customId: Int, employeeId: Int) {
        managerApi.assignEmployeeToCustom(customId, employeeId)
    }

    suspend fun setReportAccepted(reportId: Int) {
        return managerApi.setReportAccepted(reportId)
    }

    suspend fun setReportRejected(reportId: Int) {
        return managerApi.setReportRejected(reportId)
    }

    suspend fun saveProduct(product: Product): Product {
        return managerApi.saveProduct(product)
    }

    suspend fun saveDepartment(departmentName: String): Result<DepartmentDTO> {
        return try {
            val result = managerApi.saveDepartment(departmentName)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeDepartmentById(departmentId: Int) {
        return managerApi.removeDepartmentById(departmentId)
    }

    suspend fun getAllDepartments(): List<DepartmentDTO> {
        return managerApi.getAllDepartments()
    }

    suspend fun getAllDepartmentsForManager(managerId: Int): List<DepartmentDTO> {
        return managerApi.getAllDepartmentsForManager(managerId)
    }

    suspend fun getDepartmentsWithoutManager(managerId: Int): List<DepartmentDTO> {
        return managerApi.getDepartmentsWithoutManager(managerId)
    }

    suspend fun assignDepartmentToManager(managerId: Int, departmentId: Int) {
        return managerApi.assignDepartmentToManager(managerId, departmentId)
    }

    suspend fun removeDepartmentFromManager(managerId: Int, departmentId: Int) {
        return managerApi.removeDepartmentFromManager(managerId, departmentId)
    }
}