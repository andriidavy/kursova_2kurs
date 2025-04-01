package com.example.registration

import com.example.registration.database.manager.ManagerApi
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.message.MessageDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.ManagerProfileDTO
import com.example.registration.model.users.StaffDTO
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.kotlin.whenever

class ApiManager : ManagerApi {
    var provideProductResult: Int = 4540
    override suspend fun getAllManagersProfileDTO(token: String): List<ManagerProfileDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun loginManager(email: String, password: String): String {
        TODO("Not yet implemented")
    }

    override suspend fun insertManager(
        token: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteManagerById(token: String, managerId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getManagerProfile(token: String, managerId: Int): ManagerProfileDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCustomsWithoutEmployee(
        token: String,
        managerId: Int,
        page: Int,
        size: Int
    ): List<CustomDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun searchCustomById(token: String, customId: Int): CustomDTO {
        TODO("Not yet implemented")
    }

    override suspend fun getAllWaiting(token: String, managerId: Int): List<ReportDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCustoms(token: String, page: Int, size: Int): List<CustomDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCustomsWithMessage(
        token: String,
        managerId: Int,
        page: Int,
        size: Int
    ): List<CustomDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCustomsWithDepartment(
        token: String,
        managerId: Int,
        page: Int,
        size: Int
    ): List<CustomDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllEmployeesProfile(token: String): List<EmployeeProfileDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun insertEmployee(
        token: String,
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Int {
        TODO("Not yet implemented")
    }

    override suspend fun deleteEmployeeById(token: String, employeeId: Int): Int {
        return 10
    }

    override suspend fun getStaff(token: String): List<StaffDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllProducts(token: String, page: Int, size: Int): List<ProductDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun searchProduct(
        token: String,
        searchStr: String,
        chooseType: Int,
        page: Int,
        size: Int
    ): List<ProductDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun searchProductByName(token: String, productName: String): List<ProductDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun assignEmployeeToCustom(token: String, customId: Int, employeeId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun setReportAccepted(token: String, reportId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun existsCustomInDepartment(
        token: String,
        managerId: Int,
        customId: Int
    ): Int {
        TODO("Not yet implemented")
    }

    override suspend fun setReportRejected(token: String, reportId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun provideProduct(
        token: String,
        productName: String,
        quantity: Int,
        price: Double,
        description: String
    ): Int {
        return provideProductResult
    }

    override suspend fun updateProduct(
        token: String,
        productId: Int,
        productName: String,
        description: String,
        quantity: Int,
        price: Double
    ): ProductDTO {
        return ProductDTO(4540,"Updated product", "Test Description", 100, 50.55)
    }

    override suspend fun isProductExists(productId: String, warehouseId: String): Boolean {
        return true // всегда возвращаем true для теста
    }

    override suspend fun saveDepartment(token: String, departmentName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDepartments(token: String): List<DepartmentDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllDepartmentsForManager(
        token: String,
        managerId: Int
    ): List<DepartmentDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun getDepartmentsWithoutManager(
        token: String,
        managerId: Int
    ): List<DepartmentDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun removeDepartmentFromManager(
        token: String,
        managerId: Int,
        departmentId: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun assignDepartmentToManager(
        token: String,
        managerId: Int,
        departmentId: Int
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun getMessageForCustom(token: String, customId: Int): List<MessageDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun sendMessageByManager(
        token: String,
        customId: Int,
        senderId: Int,
        text: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun closeChat(token: String, customId: Int) {
        TODO("Not yet implemented")
    }
}

class ManagerRepositoryTest {

    private lateinit var managerRepository: ManagerRepository

    @Before
    fun setUp() {
        val api = ApiManager()
        managerRepository = ManagerRepository(api)
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun `isProductExists returns true when product exists`() = runTest {
//        val token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9NQU5BR0VSIiwiaWQiOjQsInN1YiI6InRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzQzNDQ2ODY5LCJleHAiOjE3NDM0NTA0Njl9.gnmYK1-yH15J1h8nDN_ntC0vbebQ0yCMWZci9IMhkoE"
//        val productName = "StellarGlow Skincare Set"
//        val result =
//            managerRepository.isProductExists(token, productName).first()
//        println("Результат теста 1: $result")
//        assertEquals(Result.success(true), result)
//    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `provideProduct returns success when product is added`() = runTest {
        //тест операції типу CREATE
        val token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9NQU5BR0VSIiwiaWQiOjQsInN1YiI6InRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzQzNDQ2ODY5LCJleHAiOjE3NDM0NTA0Njl9.gnmYK1-yH15J1h8nDN_ntC0vbebQ0yCMWZci9IMhkoE"
        val productName = "New product"
        val quantity = 10
        val price = 99.99
        val description = "Test Description"
        val productId = 4540

        val addResult = managerRepository.provideProduct(token, productName, quantity, price, description).first()
        println("Результат додавання продукту до БД: $addResult")
        assertEquals(Result.success(productId), addResult)

        //тест операції типу READ
        val isProductExists = managerRepository.isProductExists(token, productName).first()
        println("Результат перевірки наявності продукту в БД: $isProductExists")
        assertEquals(Result.success(true), isProductExists)

        val newProductName = "Updated product"
        val newQuantity = 100
        val newPrice = 50.55

        //тест операції типу UPDATE
        val updResult = managerRepository.updateProduct(token, productId, newProductName, description, newQuantity, newPrice).first()
        var successResult = ProductDTO()
        updResult.onSuccess {res -> successResult = res}
        println("Результат оновлення продукту в БД: $successResult")
        assertEquals(newProductName, successResult.name)

        //тест операції типу DELETE
        val employeeIdForDelete = 10
        val deleteResult = managerRepository.deleteEmployeeById(token, employeeIdForDelete)
        println("Видалено обліковий запис робітника з id: $deleteResult")
        assertEquals(employeeIdForDelete, deleteResult)
    }
}