package com.example.registration.database.myIsam.manager

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.ManagerProfileDTO
import com.example.registration.model.users.StaffDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MiManagerApi {
    @GET("/mi/manager/profile/get-all")
    suspend fun getAllManagersProfileDTO(): List<ManagerProfileDTO>

    @GET("/mi/manager/login")
    suspend fun loginManager(
        @Query("email") email: String,
        @Query("password") password: String
    ): Int

    @POST("/mi/manager/insert")
    suspend fun insertManager(
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("repPassword") repPassword: String
    ): Int

    @DELETE("/mi/manager/delete-manager-by-id")
    suspend fun deleteManagerById(@Query("managerId") managerId: Int)

    @GET("/mi/manager/get-manager-by-id")
    suspend fun getManagerProfile(@Query("managerId") managerId: Int): ManagerProfileDTO

    @GET("/mi/manager/get-customs-without-employee")
    suspend fun getAllCustomsWithoutEmployee(@Query("managerId") managerId: Int): List<CustomDTO>

    @GET("/mi/manager/search-custom-by-id")
    suspend fun searchCustomById(@Query("customId") customId: Int): CustomDTO

    @GET("/mi/manager/get-waiting-reports")
    suspend fun getAllWaiting(@Query("managerId") managerId: Int): List<ReportDTO>

    @GET("/mi/manager/get-all-customs")
    suspend fun getAllCustoms(): List<CustomDTO>

    @GET("/mi/manager/employee/profile/get-all")
    suspend fun getAllEmployeesProfile(): List<EmployeeProfileDTO>

    @POST("/mi/manager/employee/insert")
    suspend fun insertEmployee(
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("repPassword") repPassword: String
    ): Int

    @DELETE("/mi/manager/employee/delete-by-id")
    suspend fun deleteEmployeeById(@Query("employeeId") employeeId: Int)

    @GET("/mi/manager/get-staff")
    suspend fun getStaff(): List<StaffDTO>

    @GET("/mi/manager/get-all-product")
    suspend fun getAllProducts(): List<ProductDTO>

    @GET("/mi/manager/search-product-by-id")
    suspend fun searchProductById(@Query("productId") productId: Int): ProductDTO

    @POST("/mi/manager/assign-employee-to-custom")
    suspend fun assignEmployeeToCustom(
        @Query("employeeId") employeeId: Int,
        @Query("customId") customId: Int

    )

    @POST("/mi/manager/set-report-accepted")
    suspend fun setReportAccepted(@Query("reportId") reportId: Int)

    @POST("/mi/manager/set-report-rejected")
    suspend fun setReportRejected(@Query("reportId") reportId: Int)

    @POST("/mi/manager/provide-product")
    suspend fun provideProduct(
        @Query("productName") productName: String,
        @Query("quantity") quantity: Int,
        @Query("price") price: Double,
        @Query("description") description: String
    ): Int

    @GET("/mi/manager/is-product-exist")
    suspend fun isProductExists(@Query("productName") productName: String): Boolean

    @POST("/mi/manager/insert-department")
    suspend fun saveDepartment(@Query("departmentName") departmentName: String)

    @GET("/mi/manager/department/get-all")
    suspend fun getAllDepartments(): List<DepartmentDTO>

    @GET("/mi/manager/get-departments-for-manager")
    suspend fun getAllDepartmentsForManager(@Query("managerId") managerId: Int): List<DepartmentDTO>

    @GET("/mi/manager/get-departments-without-manager")
    suspend fun getDepartmentsWithoutManager(@Query("managerId") managerId: Int): List<DepartmentDTO>

    @DELETE("/mi/manager/remove-department-for-manager")
    suspend fun removeDepartmentFromManager(
        @Query("managerId") managerId: Int,
        @Query("departmentId") departmentId: Int
    )

    @POST("/mi/manager/assign-manager-to-department")
    suspend fun assignDepartmentToManager(
        @Query("managerId") managerId: Int,
        @Query("departmentId") departmentId: Int
    )
}