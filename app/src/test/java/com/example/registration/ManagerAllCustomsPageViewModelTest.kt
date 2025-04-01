package com.example.registration


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.registration.database.manager.ManagerApi
import com.example.registration.database.manager.ManagerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.registration.datastore.DatastoreRepo
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.message.MessageDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.report.ReportDTO
import com.example.registration.model.users.EmployeeProfileDTO
import com.example.registration.model.users.ManagerProfileDTO
import com.example.registration.model.users.StaffDTO
import com.example.registration.ui.manager.allCustoms.ManagerAllCustomsPageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import org.junit.After

/** Фейковый репозиторий для тестирования */

class ManageApi : ManagerApi {
    // Фейковые методы, которые возвращают нужные данные для тестирования
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
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }

    override suspend fun updateProduct(
        token: String,
        productId: Int,
        productName: String,
        description: String,
        quantity: Int,
        price: Double
    ): ProductDTO {
        TODO("Not yet implemented")
    }

    override suspend fun isProductExists(token: String, productName: String): Boolean {
        TODO("Not yet implemented")
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
class ManageRepository(managerApi: ManagerApi) : ManagerRepository(managerApi) {
    private val fakeData = List(30) {
        CustomDTO().apply { customId = it } // Используем сеттер вместо конструктора
    }

    private val _dataFlow = MutableStateFlow(fakeData)

    override fun getAllCustoms(token: String, page: Int, pageSize: Int): Flow<List<CustomDTO>> {
        val startIndex = page * pageSize
        val endIndex = minOf(startIndex + pageSize, fakeData.size)
        return flowOf(fakeData.subList(startIndex, endIndex))
    }

    override fun getAllCustomsWithMessage(
        token: String, managerId: Int, page: Int, pageSize: Int
    ): Flow<List<CustomDTO>> = getAllCustoms(token, page, pageSize)

    override fun getAllCustomsWithDepartment(
        token: String, managerId: Int, page: Int, pageSize: Int
    ): Flow<List<CustomDTO>> = getAllCustoms(token, page, pageSize)

    override fun searchCustomById(token: String, customId: Int): Flow<Result<CustomDTO>> {
        val custom = fakeData.find { it.customId == customId } // Исправлено
        return flowOf(custom?.let { Result.success(it) } ?: Result.failure(Exception("Not Found")))
    }
}

/** Фейковый DatastoreRepo */
class DatastoreRep : DatastoreRepo {
    override suspend fun putInt(key: String, value: Int) {
        // Реализуем фейковую логику, если необходимо
    }

    override suspend fun getInt(key: String): Int? {
        return 123 // Фейковое значение
    }

    override suspend fun putString(key: String, value: String) {
        // Реализуем фейковую логику
    }

    override suspend fun getString(key: String): String? {
        return "fake_token" // Фейковый токен
    }

    override suspend fun clearIntPreferences(key: String) {
        // Реализуем фейковую логику
    }

    override suspend fun clearStringPreferences(key: String) {
        // Реализуем фейковую логику
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class ManagerAllCustomsPageViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ManagerAllCustomsPageViewModel
    private lateinit var repository: ManageRepository
    private lateinit var datastoreRepo: DatastoreRep
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() = runTest {
        Dispatchers.setMain(testDispatcher)

        val managerApi = ManageApi()
        repository = ManageRepository(managerApi)
        datastoreRepo = DatastoreRep()
        viewModel = ManagerAllCustomsPageViewModel(repository, datastoreRepo)
    }

    @Test
    fun `loadNextPage increases currentPage`() = runTest(testDispatcher) {
        println("Початок тесту: loadNextPage increases currentPage")

        println("Перевіряємо, що поточна сторінка дорівнює 0")
        assertEquals(0, viewModel.currentPage)

        println("Викликаємо loadNextPage()")
        viewModel.loadNextPage()
        advanceUntilIdle()

        println("Перевіряємо, що поточна сторінка дорівнює 1")
        assertEquals(1, viewModel.currentPage)

        println("Викликаємо loadNextPage() ще раз")
        viewModel.loadNextPage()
        advanceUntilIdle()

        println("Перевіряємо, що поточна сторінка дорівнює 2")
        assertEquals(2, viewModel.currentPage)

        println("Тест успішно завершено\n")
    }

    @Test
    fun `loadPreviousPage decreases currentPage but not below zero`() = runTest(testDispatcher) {
        println("Початок тесту: loadPreviousPage decreases currentPage but not below zero")

        println("Викликаємо loadNextPage(), щоб збільшити поточну сторінку")
        viewModel.loadNextPage()
        advanceUntilIdle()

        println("Викликаємо loadPreviousPage()")
        viewModel.loadPreviousPage()
        advanceUntilIdle()

        println("Перевіряємо, що поточна сторінка дорівнює 0")
        assertEquals(0, viewModel.currentPage)

        println("Викликаємо loadPreviousPage() ще раз (не повинно стати -1)")
        viewModel.loadPreviousPage()
        advanceUntilIdle()

        println("Перевіряємо, що поточна сторінка залишається 0")
        assertEquals(0, viewModel.currentPage)

        println("Тест успішно завершено\n")
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}