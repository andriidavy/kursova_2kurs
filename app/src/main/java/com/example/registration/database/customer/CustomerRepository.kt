package com.example.registration.database.customer

import com.example.registration.model.cart.CartProductDTO
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.users.CustomerProfileDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CustomerRepository @Inject constructor(
    private val customerApi: CustomerApi
) {

    fun insertCustomer(
        name: String,
        surname: String,
        email: String,
        password: String,
        repPassword: String
    ): Flow<Result<Int>> = flow {
        emit(
            try {
                val customer = customerApi.insertCustomer(name, surname, email, password, repPassword)
                Result.success(customer)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun loginCustomer(email: String, password: String): Flow<Result<Int>> = flow {
        emit(
            try {
                val customer = customerApi.loginCustomer(email, password)
                Result.success(customer)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun getCustomerProfileById(customerId: Int): CustomerProfileDTO {
        return customerApi.getCustomerProfileById(customerId)
    }

    fun getProducts(): Flow<List<ProductDTO>> = flow {
        emit(customerApi.getProductsAll())
    }

    fun searchProduct(searchStr: String, chooseType: Int): Flow<List<ProductDTO>> = flow {
        emit(customerApi.searchProduct(searchStr, chooseType))
    }

    fun searchProductWithPriceRange(
        searchStr: String,
        chooseType: Int,
        minPrice: Double,
        maxPrice: Double
    ): Flow<List<ProductDTO>> = flow {
        emit(customerApi.searchProductWithPriceRange(searchStr, chooseType, minPrice, maxPrice))
    }

    fun getMinProductPrice(): Flow<Double> = flow {
        emit(customerApi.getMinProductPrice())
    }

    fun getMaxProductPrice(): Flow<Double> = flow {
        emit(customerApi.getMaxProductPrice())
    }

    fun getCartProducts(customerId: Int): Flow<List<CartProductDTO>> = flow {
        emit(customerApi.getCartProducts(customerId))
    }

    fun getCustomsForCustomer(customerId: Int): Flow<List<CustomDTO>> = flow {
        emit(customerApi.getCustomsForCustomer(customerId))
    }

    suspend fun addProductToCart(customerId: Int, productId: Int, quantity: Int): Result<Unit> {
        return try {
            customerApi.addProductToCart(customerId, productId, quantity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    fun createCustom(customerId: Int, departmentId: Int): Flow<Result<Int>> = flow {
        emit(
            try {
                val result: Int = customerApi.createCustom(customerId, departmentId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun removeProductFromCart(customerId: Int, productId: Int) {
        return customerApi.removeProductFromCart(customerId, productId)
    }

    suspend fun clearCart(customerId: Int) {
        return customerApi.clearCart(customerId)
    }

    suspend fun getAllDepartments(): List<DepartmentDTO> {
        return customerApi.getAllDepartments()
    }
}

