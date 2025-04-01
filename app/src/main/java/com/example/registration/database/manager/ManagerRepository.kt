package com.example.registration.database.manager

import com.example.registration.global.LoginResponse
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.message.MessageDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.ManagerProfileDTO
import com.example.registration.model.users.StaffDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class ManagerRepository @Inject constructor(private val managerApi: ManagerApi) {

    fun loginManager(email: String, password: String): Flow<Result<String>> = flow {
        emit(
            try {
                val manager = managerApi.loginManager(email, password)
                Result.success(manager)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getAllManagersProfileDTO(token: String): Flow<List<ManagerProfileDTO>> = flow {
        emit(managerApi.getAllManagersProfileDTO(token))
    }

    fun getManagerProfile(token: String, managerId: Int): Flow<ManagerProfileDTO> = flow {
        emit(managerApi.getManagerProfile(token, managerId))
    }

    fun insertManager(
        token: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> = flow {
        emit(
            try {
                val manager =
                    managerApi.insertManager(token, name, surname, email, password, repPassword)
                Result.success(manager)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun deleteManagerById(token: String, managerId: Int): Flow<Result<Unit>> = flow {
        emit(
            try {
                val result = managerApi.deleteManagerById(token, managerId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    open fun getAllCustoms(token: String, page: Int, size: Int): Flow<List<CustomDTO>> = flow {
        emit(managerApi.getAllCustoms(token, page, size))
    }

    open fun getAllCustomsWithMessage(
        token: String,
        managerId: Int,
        page: Int,
        size: Int
    ): Flow<List<CustomDTO>> = flow {
        emit(managerApi.getAllCustomsWithMessage(token, managerId, page, size))
    }

    open fun getAllCustomsWithDepartment(
        token: String,
        managerId: Int,
        page: Int,
        size: Int
    ): Flow<List<CustomDTO>> = flow {
        emit(managerApi.getAllCustomsWithDepartment(token, managerId, page, size))
    }

    fun existsCustomInDepartment(token: String, managerId: Int, customId: Int): Flow<Result<Int>> =
        flow {
            emit(
                try {
                    val isExist = managerApi.existsCustomInDepartment(token, managerId, customId)
                    Result.success(isExist)

                } catch (e: Exception) {
                    Result.failure(e)
                }
            )
        }

    fun getAllProducts(token: String, page: Int, size: Int): Flow<List<ProductDTO>> = flow {
        emit(managerApi.getAllProducts(token, page, size))
    }

    fun searchProduct(
        token: String,
        searchStr: String,
        chooseType: Int,
        page: Int,
        size: Int
    ): Flow<List<ProductDTO>> = flow {
        emit(managerApi.searchProduct(token, searchStr, chooseType, page, size))
    }

    fun getAllCustomsWithoutEmployee(
        token: String,
        managerId: Int,
        page: Int,
        size: Int
    ): Flow<List<CustomDTO>> = flow {
        emit(managerApi.getAllCustomsWithoutEmployee(token, managerId, page, size))
    }

    open fun searchCustomById(token: String, customId: Int): Flow<Result<CustomDTO>> = flow {
        emit(
            try {
                val result = managerApi.searchCustomById(token, customId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getAllWaiting(token: String, managerId: Int): Flow<List<ReportDTO>> = flow {
        emit(managerApi.getAllWaiting(token, managerId))
    }

    fun getAllEmployeesProfile(token: String): Flow<List<EmployeeProfileDTO>> = flow {
        emit(managerApi.getAllEmployeesProfile(token))
    }

    fun insertEmployee(
        token: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> = flow {
        emit(
            try {
                val employee =
                    managerApi.insertEmployee(token, name, surname, email, password, repPassword)
                Result.success(employee)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun deleteEmployeeById(token: String, employeeId: Int): Int {
        return managerApi.deleteEmployeeById(token, employeeId)
    }

    suspend fun assignEmployeeToCustom(token: String, customId: Int, employeeId: Int) {
        managerApi.assignEmployeeToCustom(token, customId, employeeId)
    }

    fun getStaff(token: String): Flow<List<StaffDTO>> = flow {
        emit(managerApi.getStaff(token))
    }

    suspend fun setReportAccepted(token: String, reportId: Int) {
        return managerApi.setReportAccepted(token, reportId)
    }

    suspend fun setReportRejected(token: String, reportId: Int) {
        return managerApi.setReportRejected(token, reportId)
    }

    fun provideProduct(
        token: String,
        productName: String,
        quantity: Int,
        price: Double,
        description: String
    ): Flow<Result<Int>> = flow {
        emit(
            try {
                val result =
                    managerApi.provideProduct(token, productName, quantity, price, description)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun updateProduct(
        token: String,
        productId: Int,
        productName: String,
        description: String,
        quantity: Int,
        price: Double
    ): Flow<Result<ProductDTO>> = flow {
        emit(
            try {
                val result = managerApi.updateProduct(
                    token,
                    productId,
                    productName,
                    description,
                    quantity,
                    price
                )
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun isProductExists(token: String, productName: String): Flow<Result<Boolean>> = flow {
        emit(
            try {
                val result = managerApi.isProductExists(token, productName)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun saveDepartment(token: String, departmentName: String): Flow<Result<Unit>> = flow {
        emit(
            try {
                val result = managerApi.saveDepartment(token, departmentName)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getAllDepartments(token: String): Flow<List<DepartmentDTO>> = flow {
        emit(managerApi.getAllDepartments(token))
    }

    fun getAllDepartmentsForManager(token: String, managerId: Int): Flow<List<DepartmentDTO>> =
        flow {
            emit(managerApi.getAllDepartmentsForManager(token, managerId))
        }

    fun getDepartmentsWithoutManager(token: String, managerId: Int): Flow<List<DepartmentDTO>> =
        flow {
            emit(managerApi.getDepartmentsWithoutManager(token, managerId))
        }

    fun assignDepartmentToManager(
        token: String,
        managerId: Int,
        departmentId: Int
    ): Flow<Result<Unit>> =
        flow {
            emit(
                try {
                    val result =
                        managerApi.assignDepartmentToManager(token, managerId, departmentId)
                    Result.success(result)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            )
        }

    fun removeDepartmentFromManager(
        token: String,
        managerId: Int,
        departmentId: Int
    ): Flow<Result<Unit>> = flow {
        emit(
            try {
                val result = managerApi.removeDepartmentFromManager(token, managerId, departmentId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun getMessageForCustom(token: String, customId: Int): Flow<List<MessageDTO>> = flow {
        emit(managerApi.getMessageForCustom(token, customId))
    }

    suspend fun sendMessageByManager(token: String, customId: Int, senderId: Int, text: String) {
        managerApi.sendMessageByManager(token, customId, senderId, text)
    }

    suspend fun closeChat(token: String, customId: Int) {
        managerApi.closeChat(token, customId)
    }
}