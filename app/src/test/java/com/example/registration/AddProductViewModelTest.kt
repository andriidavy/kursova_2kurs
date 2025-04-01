//package com.example.registration
//
//import app.cash.turbine.test
//import com.example.registration.database.manager.ManagerRepository
//import com.example.registration.datastore.DatastoreRepo
//import com.example.registration.ui.manager.products.AddProductViewModel
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.test.runTest
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.ArgumentMatchers.any
//import org.mockito.ArgumentMatchers.eq
//import org.mockito.kotlin.mock
//import org.mockito.kotlin.whenever
//import org.junit.Assert.assertEquals
//
//@ExperimentalCoroutinesApi
//class AddProductViewModelTest {
//
//    @get:Rule
//    val dispatcherRule = TestCoroutineRule() // правило для тестирования корутин
//
//    private lateinit var viewModel: AddProductViewModel
//    private val managerRepository: ManagerRepository = mock()
//    private val datastoreRepository: DatastoreRepo = mock()
//
//    @Before
//    fun setUp() {
//        viewModel = AddProductViewModel(managerRepository, datastoreRepository)
//    }
//
//    @Test
//    fun `isProductExists returns true when product exists`() = runTest {
//        val token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9NQU5BR0VSIiwiaWQiOjQsInN1YiI6InRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzQzNDQ2ODY5LCJleHAiOjE3NDM0NTA0Njl9.gnmYK1-yH15J1h8nDN_ntC0vbebQ0yCMWZci9IMhkoE"
//        val productName = "Test Product"
//        whenever(managerRepository.isProductExists(token, productName))
//            .thenReturn(flowOf(Result.success(true)))
//
//        viewModel.isProductExists(productName).test {
//            val result = awaitItem()
//            println("Результат теста 1: $result")
//            assertEquals(Result.success(true), result)
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `isProductExists returns false when product does not exist`() = runTest {
//        val token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9NQU5BR0VSIiwiaWQiOjQsInN1YiI6InRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzQzNDQ2ODY5LCJleHAiOjE3NDM0NTA0Njl9.gnmYK1-yH15J1h8nDN_ntC0vbebQ0yCMWZci9IMhkoE"
//        val productName = "New Product"
//        whenever(managerRepository.isProductExists(token, productName))
//            .thenReturn(flowOf(Result.success(false)))
//
//        viewModel.isProductExists(productName).test {
//            val result = awaitItem()
//            println("Результат теста 2: $result")
//            assertEquals(Result.success(false), result)
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `provideProduct returns success when product is added`() = runTest {
//        val token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9NQU5BR0VSIiwiaWQiOjQsInN1YiI6InRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzQzNDQ2ODY5LCJleHAiOjE3NDM0NTA0Njl9.gnmYK1-yH15J1h8nDN_ntC0vbebQ0yCMWZci9IMhkoE"
//        val productId = 1
//        val name = "New Product"
//        val quantity = 10
//        val price = 99.99
//        val description = "Test Description"
//
//        whenever(managerRepository.provideProduct(token, name, quantity, price, description))
//            .thenReturn(flowOf(Result.success(productId)))
//
//        viewModel.provideProduct(name, quantity, price, description).test {
//            val result = awaitItem()
//            println("Результат теста 3: $result")
//            assertEquals(Result.success(productId), result)
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//
//    @Test
//    fun `provideProduct returns failure on error`() = runTest {
//        val token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjoiUk9MRV9NQU5BR0VSIiwiaWQiOjQsInN1YiI6InRlc3RAZ21haWwuY29tIiwiaWF0IjoxNzQzNDQ2ODY5LCJleHAiOjE3NDM0NTA0Njl9.gnmYK1-yH15J1h8nDN_ntC0vbebQ0yCMWZci9IMhkoE"
//        val name = "New Product"
//        val quantity = 10
//        val price = 99.99
//        val description = "Test Description"
//        val exception = RuntimeException("Error")
//
//        whenever(managerRepository.provideProduct(token, name, quantity, price, description))
//            .thenReturn(flowOf(Result.failure(exception)))
//
//        viewModel.provideProduct(name, quantity, price, description).test {
//            val result = awaitItem()
//            println("Результат теста 4: $result")
//            assertEquals(Result.failure<Int>(exception), result)
//            cancelAndIgnoreRemainingEvents()
//        }
//    }
//}