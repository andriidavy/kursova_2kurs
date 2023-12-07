package com.example.registration.database.myIsam.manager

import com.example.registration.database.manager.ManagerApi
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.ManagerProfileDTO
import com.example.registration.model.users.StaffDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MiManagerRepository @Inject constructor(private val miManagerApi: MiManagerApi) {

    fun loginManager(email: String, password: String): Flow<Result<Int>> = flow {
        emit(
            try {
                val manager = miManagerApi.loginManager(email, password)
                Result.success(manager)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getAllManagersProfileDTO(): Flow<List<ManagerProfileDTO>> = flow {
        emit(miManagerApi.getAllManagersProfileDTO())
    }

    fun getManagerProfile(managerId: Int): Flow<ManagerProfileDTO> = flow {
        emit(miManagerApi.getManagerProfile(managerId))
    }

    fun insertManager(
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> = flow {
        emit(
            try {
                val manager = miManagerApi.insertManager(name, surname, email, password, repPassword)
                Result.success(manager)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun deleteManagerById(managerId: Int): Flow<Result<Unit>> = flow {
        emit(
            try {
                val result = miManagerApi.deleteManagerById(managerId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getAllCustoms(): Flow<List<CustomDTO>> = flow {
        emit(miManagerApi.getAllCustoms())
    }

    fun getAllProducts(): Flow<List<ProductDTO>> = flow {
        emit(miManagerApi.getAllProducts())
    }

    fun searchProductById(productId: Int): Flow<Result<ProductDTO>> = flow {
        emit(
            try {
                val result = miManagerApi.searchProductById(productId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getAllCustomsWithoutEmployee(managerId: Int): Flow<List<CustomDTO>> = flow {
        emit(miManagerApi.getAllCustomsWithoutEmployee(managerId))
    }

    fun searchCustomById(customId: Int): Flow<Result<CustomDTO>> = flow {
        emit(
            try {
                val result = miManagerApi.searchCustomById(customId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getAllWaiting(managerId: Int): Flow<List<ReportDTO>> = flow {
        emit(miManagerApi.getAllWaiting(managerId))
    }

    fun getAllEmployeesProfile(): Flow<List<EmployeeProfileDTO>> = flow {
        emit(miManagerApi.getAllEmployeesProfile())
    }

    fun insertEmployee(
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> = flow {
        emit(
            try {
                val employee =
                    miManagerApi.insertEmployee(name, surname, email, password, repPassword)
                Result.success(employee)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun deleteEmployeeById(employeeId: Int) {
        return miManagerApi.deleteEmployeeById(employeeId)
    }

    suspend fun assignEmployeeToCustom(customId: Int, employeeId: Int) {
        miManagerApi.assignEmployeeToCustom(customId, employeeId)
    }

    fun getStaff(): Flow<List<StaffDTO>> = flow {
        emit(miManagerApi.getStaff())
    }

    suspend fun setReportAccepted(reportId: Int) {
        return miManagerApi.setReportAccepted(reportId)
    }

    suspend fun setReportRejected(reportId: Int) {
        return miManagerApi.setReportRejected(reportId)
    }

    fun provideProduct(
        productName: String,
        quantity: Int,
        price: Double,
        description: String
    ): Flow<Result<Int>> = flow {
        emit(
            try {
                val result = miManagerApi.provideProduct(productName, quantity, price, description)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun isProductExists(productName: String): Flow<Result<Boolean>> = flow {
        emit(
            try {
                val result = miManagerApi.isProductExists(productName)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun saveDepartment(departmentName: String): Flow<Result<Unit>> = flow {
        emit(
            try {
                val result = miManagerApi.saveDepartment(departmentName)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getAllDepartments(): Flow<List<DepartmentDTO>> = flow {
        emit(miManagerApi.getAllDepartments())
    }

    fun getAllDepartmentsForManager(managerId: Int): Flow<List<DepartmentDTO>> = flow {
        emit(miManagerApi.getAllDepartmentsForManager(managerId))
    }

    fun getDepartmentsWithoutManager(managerId: Int): Flow<List<DepartmentDTO>> = flow {
        emit(miManagerApi.getDepartmentsWithoutManager(managerId))
    }

    fun assignDepartmentToManager(managerId: Int, departmentId: Int): Flow<Result<Unit>> =
        flow {
            emit(
                try {
                    val result = miManagerApi.assignDepartmentToManager(managerId, departmentId)
                    Result.success(result)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            )
        }

    fun removeDepartmentFromManager(managerId: Int, departmentId: Int): Flow<Result<Unit>> = flow {
        emit(
            try {
                val result = miManagerApi.removeDepartmentFromManager(managerId, departmentId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }
}