package com.example.registration.database.manager

import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.message.MessageDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.ManagerProfileDTO
import com.example.registration.model.users.StaffDTO
import retrofit2.http.*

interface ManagerApi {
    @GET("/auth/manager/profile/get-all")
    suspend fun getAllManagersProfileDTO(@Header("Authorization") token: String): List<ManagerProfileDTO>

    @GET("/auth/manager/login")
    suspend fun loginManager(
        @Query("email") email: String,
        @Query("password") password: String
    ): String

    @POST("/auth/manager/insert")
    suspend fun insertManager(
        @Header("Authorization") token: String,
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("repPassword") repPassword: String
    ): Int


    @DELETE("/auth/manager/delete-manager-by-id")
    suspend fun deleteManagerById(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int
    )


    @GET("/auth/manager/get-manager-by-id")
    suspend fun getManagerProfile(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int
    ): ManagerProfileDTO

    @GET("/order-processing/manager/get-customs-without-employee")
    suspend fun getAllCustomsWithoutEmployee(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<CustomDTO>

    @GET("/order-processing/manager/search-custom-by-id")
    suspend fun searchCustomById(
        @Header("Authorization") token: String,
        @Query("customId") customId: Int
    ): CustomDTO

    @GET("/order-processing/manager/custom/report/get-waiting")
    suspend fun getAllWaiting(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int
    ): List<ReportDTO>

    @GET("/order-processing/manager/custom/get-all")
    suspend fun getAllCustoms(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<CustomDTO>

    @GET("/order-processing/manager/get-customs-with-message")
    suspend fun getAllCustomsWithMessage(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<CustomDTO>

    @GET("/order-processing/manager/get-customs-with-department")
    suspend fun getAllCustomsWithDepartment(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<CustomDTO>

    @GET("/auth/manager/employee/profile/get-all")
    suspend fun getAllEmployeesProfile(@Header("Authorization") token: String): List<EmployeeProfileDTO>

    @POST("/auth/manager/employee/insert")
    suspend fun insertEmployee(
        @Header("Authorization") token: String,
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("repPassword") repPassword: String
    ): Int

    @DELETE("/auth/manager/employee/delete-employee-by-id")
    suspend fun deleteEmployeeById(
        @Header("Authorization") token: String,
        @Query("employeeId") employeeId: Int
    )

    @GET("/auth/manager/get-staff")
    suspend fun getStaff(@Header("Authorization") token: String): List<StaffDTO>

    @GET("/warehouse/manager/product/get-all")
    suspend fun getAllProducts(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ProductDTO>

    @GET("/warehouse/manager/product/search")
    suspend fun searchProduct(
        @Header("Authorization") token: String,
        @Query("searchStr") searchStr: String,
        @Query("chooseType") chooseType: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ProductDTO>

    @GET("/warehouse/manager/search-product-by-name")
    suspend fun searchProductByName(
        @Header("Authorization") token: String,
        @Query("productName") productName: String
    ): List<ProductDTO>

    @POST("/order-processing/manager/custom/assign-employee")
    suspend fun assignEmployeeToCustom(
        @Header("Authorization") token: String,
        @Query("customId") customId: Int,
        @Query("employeeId") employeeId: Int
    )

    @POST("/order-processing/manager/custom/report/accept")
    suspend fun setReportAccepted(
        @Header("Authorization") token: String,
        @Query("reportId") reportId: Int
    )

    @GET("/order-processing/manager/check-custom-department")
    suspend fun existsCustomInDepartment(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int,
        @Query("customId") customId: Int
    ): Int

    @POST("/order-processing/manager/custom/report/reject")
    suspend fun setReportRejected(
        @Header("Authorization") token: String,
        @Query("reportId") reportId: Int
    )

    @POST("/warehouse/manager/provide-product")
    suspend fun provideProduct(
        @Header("Authorization") token: String,
        @Query("productName") productName: String,
        @Query("quantity") quantity: Int,
        @Query("price") price: Double,
        @Query("description") description: String
    ): Int

    @POST("/warehouse/manager/update-product")
    suspend fun updateProduct(
        @Header("Authorization") token: String,
        @Query("productId") productId: Int,
        @Query("productName") productName: String,
        @Query("description") description: String,
        @Query("quantity") quantity: Int,
        @Query("price") price: Double,
    )

    @GET("/warehouse/manager/is-product-exist")
    suspend fun isProductExists(
        @Header("Authorization") token: String,
        @Query("productName") productName: String
    ): Boolean

    @POST("/warehouse/manager/department/save")
    suspend fun saveDepartment(
        @Header("Authorization") token: String,
        @Query("departmentName") departmentName: String
    )

    @GET("/warehouse/manager/department/get-all")
    suspend fun getAllDepartments(@Header("Authorization") token: String): List<DepartmentDTO>

    @GET("/warehouse/manager/department/get-departments-for-manager")
    suspend fun getAllDepartmentsForManager(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int
    ): List<DepartmentDTO>

    @GET("/warehouse/manager/department/get-departments-non-for-manager")
    suspend fun getDepartmentsWithoutManager(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int
    ): List<DepartmentDTO>

    @DELETE("/warehouse/manager/department/remove-department-from-manager")
    suspend fun removeDepartmentFromManager(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int,
        @Query("departmentId") departmentId: Int
    )

    @POST("/warehouse/manager/department/assign-department-to-manager")
    suspend fun assignDepartmentToManager(
        @Header("Authorization") token: String,
        @Query("managerId") managerId: Int,
        @Query("departmentId") departmentId: Int
    )

    @GET("/order-processing/manager/custom/message/get-for-custom")
    suspend fun getMessageForCustom(
        @Header("Authorization") token: String,
        @Query("customId") customId: Int
    ): List<MessageDTO>

    @POST("/order-processing/manager/custom/send-message")
    suspend fun sendMessageByManager(
        @Header("Authorization") token: String,
        @Query("customId") customId: Int,
        @Query("senderId") senderId: Int,
        @Query("text") text: String
    )

    @POST("/order-processing/manager/custom/close-chat")
    suspend fun closeChat(
    @Header("Authorization") token: String,
    @Query("customId") customId: Int
    )
}