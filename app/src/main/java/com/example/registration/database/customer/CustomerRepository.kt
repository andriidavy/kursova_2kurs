package com.example.registration.database.customer

import com.example.registration.global.LoginResponse
import com.example.registration.model.cart.CartProductDTO
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.message.MessageDTO
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
                val customer =
                    customerApi.insertCustomer(name, surname, email, password, repPassword)
                Result.success(customer)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun loginCustomer(email: String, password: String): Flow<Result<String>> = flow {
        emit(
            try {
                val customer = customerApi.loginCustomer(email, password)
                Result.success(customer)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun getCustomerProfileById(token: String, customerId: Int): CustomerProfileDTO {
        return customerApi.getCustomerProfileById(token, customerId)
    }

    fun getProductsPage(token: String, page: Int, size: Int): Flow<List<ProductDTO>> = flow {
        emit(customerApi.getProductsAllPage(token, page, size))
    }

    fun searchProduct(token: String, searchStr: String, chooseType: Int, page: Int, size: Int): Flow<List<ProductDTO>> = flow {
        emit(customerApi.searchProduct(token, searchStr, chooseType, page, size))
    }

    fun searchProductWithPriceRange(
        token: String,
        searchStr: String,
        chooseType: Int,
        minPrice: Double,
        maxPrice: Double,
        page: Int,
        size: Int
    ): Flow<List<ProductDTO>> = flow {
        emit(customerApi.searchProductWithPriceRange(token, searchStr, chooseType, minPrice, maxPrice, page, size))
    }

    fun getMinProductPrice(token: String): Flow<Double> = flow {
        emit(customerApi.getMinProductPrice(token))
    }

    fun getMaxProductPrice(token: String): Flow<Double> = flow {
        emit(customerApi.getMaxProductPrice(token))
    }

    fun getCartProducts(token: String, customerId: Int): Flow<List<CartProductDTO>> = flow {
        emit(customerApi.getCartProducts(token, customerId))
    }

    fun getCustomsForCustomer(token: String, customerId: Int): Flow<List<CustomDTO>> = flow {
        emit(customerApi.getCustomsForCustomer(token, customerId))
    }

    fun addProductToCart(token: String, customerId: Int, productId: Int, quantity: Int): Flow<Result<Unit>> =
        flow {
            emit(
                try {
                    customerApi.addProductToCart(token, customerId, productId, quantity)
                    Result.success(Unit)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            )
        }


    fun createCustom(token: String, customerId: Int, departmentId: Int): Flow<Result<Int>> = flow {
        emit(
            try {
                val result: Int = customerApi.createCustom(token, customerId, departmentId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun removeProductFromCart(token: String, customerId: Int, productId: Int) {
        return customerApi.removeProductFromCart(token, customerId, productId)
    }

    suspend fun clearCart(token: String, customerId: Int) {
        return customerApi.clearCart(token, customerId)
    }

    suspend fun getAllDepartments(token: String): List<DepartmentDTO> {
        return customerApi.getAllDepartments(token)
    }

    fun getMessageForCustom(token: String, customId: Int): Flow<List<MessageDTO>> = flow {
        emit(customerApi.getMessageForCustom(token, customId))
    }
}

