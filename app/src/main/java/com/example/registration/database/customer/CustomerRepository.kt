package com.example.registration.database.customer

import com.example.registration.model.cart.CartProductDTO
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.product.Product
import com.example.registration.database.customer.CustomerApi
import com.example.registration.model.users.Customer
import com.example.registration.model.users.CustomerProfileDTO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class CustomerRepository @Inject constructor(
    private val customerApi: CustomerApi
) {

    suspend fun save(
        name: String,
        surname: String,
        email: String,
        password: String
    ): Result<Customer> {
        return try {
            val customer = customerApi.save(name, surname, email, password)
            Result.success(customer)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loginCustomer(email: String, password: String): Result<Customer> {
        return try {
            val customer = customerApi.loginCustomer(email, password)
            Result.success(customer)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCustomerProfileById(customerId: Int): CustomerProfileDTO {
        return customerApi.getCustomerProfileById(customerId)
    }

    suspend fun getProducts(): List<Product> {
        return customerApi.getProductsAll()
    }

    suspend fun getCartProducts(customerId: Int): List<CartProductDTO> {
        return customerApi.getCartProducts(customerId)
    }

    suspend fun getCustomsForCustomer(customerId: Int): List<CustomDTO> {
        return customerApi.getCustomsForCustomer(customerId)
    }

    suspend fun addProductToCart(customerId: Int, productId: Int, quantity: Int): Result<Unit> {
        return try {
            customerApi.addProductToCart(customerId, productId, quantity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    suspend fun createCustom(customerId: Int): Result<Int> {
        return try {
            val result: Int = customerApi.createCustom(customerId)
            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun removeProductFromCart(customerId: Int, productId: Int) {
        return customerApi.removeProductFromCart(customerId, productId)
    }

    suspend fun clearCart(customerId: Int) {
        return customerApi.clearCart(customerId)
    }

    suspend fun assignDepartmentToCustom(customId: Int, departmentId: Int) {
        return customerApi.assignDepartmentToCustom(customId, departmentId)
    }

    suspend fun getAllDepartments(): List<DepartmentDTO> {
        return customerApi.getAllDepartments()
    }
}

