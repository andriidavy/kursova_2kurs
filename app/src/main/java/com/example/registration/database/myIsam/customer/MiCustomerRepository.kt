package com.example.registration.database.myIsam.customer

import com.example.registration.model.cart.CartProductDTO
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.users.CustomerProfileDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MiCustomerRepository  @Inject constructor(
    private val miCustomerApi: MiCustomerApi
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
                val customer = miCustomerApi.insertCustomer(name, surname, email, password, repPassword)
                Result.success(customer)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    fun loginCustomer(email: String, password: String): Flow<Result<Int>> = flow {
        emit(
            try {
                val customer = miCustomerApi.loginCustomer(email, password)
                Result.success(customer)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun getCustomerProfileById(customerId: Int): CustomerProfileDTO {
        return miCustomerApi.getCustomerProfileById(customerId)
    }

    fun getProducts(): Flow<List<ProductDTO>> = flow {
        emit(miCustomerApi.getProductsAll())
    }

    fun searchProduct(searchStr: String, chooseType: Int): Flow<List<ProductDTO>> = flow {
        emit(miCustomerApi.searchProduct(searchStr, chooseType))
    }

    fun searchProductWithPriceRange(
        searchStr: String,
        chooseType: Int,
        minPrice: Double,
        maxPrice: Double
    ): Flow<List<ProductDTO>> = flow {
        emit(miCustomerApi.searchProductWithPriceRange(searchStr, chooseType, minPrice, maxPrice))
    }

    fun getMinProductPrice(): Flow<Double> = flow {
        emit(miCustomerApi.getMinProductPrice())
    }

    fun getMaxProductPrice(): Flow<Double> = flow {
        emit(miCustomerApi.getMaxProductPrice())
    }

    fun getCartProducts(customerId: Int): Flow<List<CartProductDTO>> = flow {
        emit(miCustomerApi.getCartProducts(customerId))
    }

    fun getCustomsForCustomer(customerId: Int): Flow<List<CustomDTO>> = flow {
        emit(miCustomerApi.getCustomsForCustomer(customerId))
    }

    fun addProductToCart(customerId: Int, productId: Int, quantity: Int): Flow<Result<Unit>> =
        flow {
            emit(
                try {
                    miCustomerApi.addProductToCart(customerId, productId, quantity)
                    Result.success(Unit)
                } catch (e: Exception) {
                    Result.failure(e)
                }
            )
        }


    fun createCustom(customerId: Int, departmentId: Int): Flow<Result<Int>> = flow {
        emit(
            try {
                val result: Int = miCustomerApi.createCustom(customerId, departmentId)
                Result.success(result)
            } catch (e: Exception) {
                Result.failure(e)
            }
        )
    }

    suspend fun removeProductFromCart(customerId: Int, productId: Int) {
        return miCustomerApi.removeProductFromCart(customerId, productId)
    }

    suspend fun clearCart(customerId: Int) {
        return miCustomerApi.clearCart(customerId)
    }

    suspend fun getAllDepartments(): List<DepartmentDTO> {
        return miCustomerApi.getAllDepartments()
    }
}