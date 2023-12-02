package com.example.registration.database.manager

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.ManagerProfileDTO
import retrofit2.http.*

interface ManagerApi {
    @GET("/manager/profile/get-all")
    suspend fun getAllManagersProfileDTO(): List<ManagerProfileDTO>

    @GET("/manager/login")
    suspend fun loginManager(
        @Query("email") email: String,
        @Query("password") password: String
    ): Int

    @POST("/manager/insert")
    suspend fun insertManager(
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Int


    @DELETE("/manager/delete-manager-by-id")
    suspend fun deleteManagerById(@Query("managerId") managerId: Int)


    @GET("/manager/get-manager-by-id")
    suspend fun getManagerProfile(@Query("managerId") managerId: Int): ManagerProfileDTO

    @GET("/manager/custom/get-created")
    suspend fun getAllCreatedCustoms(@Query("managerId") managerId: Int): List<CustomDTO>

    @GET("/manager/custom/report/get-waiting")
    suspend fun getAllWaiting(@Query ("managerId") managerId: Int): List<ReportDTO>

    @GET("/manager/custom/get-all")
    suspend fun getAllCustoms(): List<CustomDTO>

    @GET("/manager/employee/profile/get-all")
    suspend fun getAllEmployeesProfile(): List<EmployeeProfileDTO>

    @POST("/manager/employee/insert")
    suspend fun insertEmployee(
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String,
        @Query("password") password: String
    ): Int

    @DELETE("/manager/employee/delete-employee-by-id")
    suspend fun deleteEmployeeById(@Query("employeeId") employeeId: Int)

    @GET("/manager/product/get-all")
    suspend fun getAllProducts(): List<ProductDTO>

    @POST("/manager/custom/assign-employee/")
    suspend fun assignEmployeeToCustom(
        @Query("customId") customId: Int,
        @Query("employeeId") employeeId: Int
    )

    @POST("/manager/custom/report/accept")
    suspend fun setReportAccepted(@Query("reportId") reportId: Int)

    @POST("/manager/custom/report/reject")
    suspend fun setReportRejected(@Query("reportId") reportId: Int)

    @POST("/manager/provide-product")
    suspend fun provideProduct(@Query("productName") productName: String, @Query("quantity") quantity: Int, @Query("price") price: Double, @Query("description") description: String): Int

    @GET("/manager/is-product-exist")
    suspend fun isProductExists(@Query("productName") productName: String): Boolean

    @POST("/manager/department/save")
    suspend fun saveDepartment(@Query("departmentName") departmentName: String): DepartmentDTO

    @DELETE("/manager/department/delete-by-id")
    suspend fun removeDepartmentById(@Query("departmentId") departmentId: Int)

    @GET("/manager/department/get-all")
    suspend fun getAllDepartments(): List<DepartmentDTO>

    @GET("/manager/department/get-departments-for-manager")
    suspend fun getAllDepartmentsForManager(@Query("managerId") managerId: Int): List<DepartmentDTO>

    @GET("/manager/department/get-departments-non-for-manager")
    suspend fun getDepartmentsWithoutManager(@Query("managerId") managerId: Int): List<DepartmentDTO>

    @POST("/manager/department/remove-department-from-manager")
    suspend fun removeDepartmentFromManager(
        @Query("managerId") managerId: Int,
        @Query("departmentId") departmentId: Int
    )

    @POST("/manager/department/assign-department-to-manager")
    suspend fun assignDepartmentToManager(
        @Query("managerId") managerId: Int,
        @Query("departmentId") departmentId: Int
    )


}