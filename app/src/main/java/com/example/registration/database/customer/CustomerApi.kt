package com.example.registration.database.customer

import com.example.registration.model.cart.CartProductDTO
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.users.CustomerProfileDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CustomerApi {
    @GET("/customer/login")
    suspend fun loginCustomer(
        @Query("email") email: String,
        @Query("password") password: String
    ): Int

    @GET("/customer/get-customer-by-id")
    suspend fun getCustomerProfileById(@Query("customerId") customerId: Int): CustomerProfileDTO

    @POST("/customer/insert")
    suspend fun insertCustomer(
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("repPassword") repPassword: String
    ): Int

    @GET("/customer/product/get-all")
    suspend fun getProductsAll(): List<ProductDTO>

    @GET("/customer/product/search")
    suspend fun searchProduct(
        @Query("searchStr") searchStr: String,
        @Query("chooseType") chooseType: Int
    ): List<ProductDTO>

    @GET("/customer/product/search-with-price-range")
    suspend fun searchProductWithPriceRange(
        @Query("searchStr") searchStr: String,
        @Query("chooseType") chooseType: Int,
        @Query("minPrice") minPrice: Double,
        @Query("maxPrice") maxPrice: Double
    ): List<ProductDTO>

    @GET("/customer/product/get-min-price")
    suspend fun getMinProductPrice(): Double

    @GET("/customer/product/get-max-price")
    suspend fun getMaxProductPrice(): Double

    @GET("/customer/get-cart")
    suspend fun getCartProducts(@Query("customerId") customerId: Int): List<CartProductDTO>

    @GET("/customer/get-customs")
    suspend fun getCustomsForCustomer(@Query("customerId") customerId: Int): List<CustomDTO>

    @POST("/customer/cart/add-product-to-cart")
    suspend fun addProductToCart(
        @Query("customerId") customerId: Int,
        @Query("productId") productId: Int,
        @Query("quantity") quantity: Int
    )

    @POST("/customer/create-custom")
    suspend fun createCustom(
        @Query("customerId") customerId: Int,
        @Query("departmentId") departmentId: Int
    ): Int

    @DELETE("/customer/cart/remove-product-by-id")
    suspend fun removeProductFromCart(
        @Query("customerId") customerId: Int,
        @Query("productId") productId: Int,
    )

    @DELETE("/customer/cart/clear")
    suspend fun clearCart(@Query("customerId") customerId: Int)


    @GET("/customer/department/get-all")
    suspend fun getAllDepartments(): List<DepartmentDTO>
}