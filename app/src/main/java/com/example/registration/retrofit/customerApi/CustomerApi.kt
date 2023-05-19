package com.example.registration.retrofit.customerApi

import com.example.registration.model.cart.CartProductDTO
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.product.Product
import com.example.registration.model.users.Customer
import com.example.registration.model.users.CustomerProfileDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CustomerApi {
    @GET("/customer/get-all")
    suspend fun getCustomersAll(): List<Customer>

    @GET("/customer/get-customer-by-id")
    suspend fun getCustomerProfileById(@Query("customerId") customerId: Int): CustomerProfileDTO

    @POST("/customer/save")
    suspend fun save(@Body customer: Customer): Customer

    @GET("/customer/product/get-all")
    suspend fun getProductsAll(): List<Product>

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
    suspend fun createCustom(@Query("customerId") customerId: Int)

    @DELETE("/customer/cart/remove-product-by-id")
    suspend fun removeProductFromCart(
        @Query("customerId") customerId: Int,
        @Query("productId") productId: Int,
    )

    @POST("/customer/cart/clear")
    suspend fun clearCart(@Query("customerId") customerId: Int)
}