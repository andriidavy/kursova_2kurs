package com.example.registration.database.customer

import com.example.registration.global.LoginResponse
import com.example.registration.model.cart.CartProductDTO
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.message.MessageDTO
import com.example.registration.model.product.ProductDTO
import com.example.registration.model.users.CustomerProfileDTO
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CustomerApi {
    @GET("/auth/customer/login")
    suspend fun loginCustomer(
        @Query("email") email: String,
        @Query("password") password: String
    ): String

    @GET("/auth/customer/get-customer-by-id")
    suspend fun getCustomerProfileById(
        @Header("Authorization") token: String,
        @Query("customerId") customerId: Int
    ): CustomerProfileDTO

    @POST("/auth/customer/insert")
    suspend fun insertCustomer(
        @Query("name") name: String,
        @Query("surname") surname: String,
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("repPassword") repPassword: String
    ): Int


    @GET("/store/customer/product/get-page")
    suspend fun getProductsAllPage(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ProductDTO>

    @GET("/store/customer/product/search")
    suspend fun searchProduct(
        @Header("Authorization") token: String,
        @Query("searchStr") searchStr: String,
        @Query("chooseType") chooseType: Int,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ProductDTO>

    @GET("/store/customer/product/search-with-price-range")
    suspend fun searchProductWithPriceRange(
        @Header("Authorization") token: String,
        @Query("searchStr") searchStr: String,
        @Query("chooseType") chooseType: Int,
        @Query("minPrice") minPrice: Double,
        @Query("maxPrice") maxPrice: Double,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): List<ProductDTO>

    @GET("/store/customer/product/get-min-price")
    suspend fun getMinProductPrice(@Header("Authorization") token: String): Double

    @GET("/store/customer/product/get-max-price")
    suspend fun getMaxProductPrice(@Header("Authorization") token: String): Double

    @GET("/store/customer/get-cart")
    suspend fun getCartProducts(
        @Header("Authorization") token: String,
        @Query("customerId") customerId: Int
    ): List<CartProductDTO>

    @GET("/store/customer/get-customs")
    suspend fun getCustomsForCustomer(
        @Header("Authorization") token: String,
        @Query("customerId") customerId: Int
    ): List<CustomDTO>

    @POST("/store/customer/cart/add-product-to-cart")
    suspend fun addProductToCart(
        @Header("Authorization") token: String,
        @Query("customerId") customerId: Int,
        @Query("productId") productId: Int,
        @Query("quantity") quantity: Int
    )

    @POST("/order-processing/customer/create-custom")
    suspend fun createCustom(
        @Header("Authorization") token: String,
        @Query("customerId") customerId: Int,
        @Query("departmentId") departmentId: Int
    ): Int

    @DELETE("/store/customer/cart/remove-product-by-id")
    suspend fun removeProductFromCart(
        @Header("Authorization") token: String,
        @Query("customerId") customerId: Int,
        @Query("productId") productId: Int,
    )

    @DELETE("/store/customer/cart/clear")
    suspend fun clearCart(
        @Header("Authorization") token: String,
        @Query("customerId") customerId: Int
    )

    @GET("/store/customer/department/get-all")
    suspend fun getAllDepartments(@Header("Authorization") token: String): List<DepartmentDTO>

    @GET("/order-processing/customer/custom/message/get-for-custom")
    suspend fun getMessageForCustom(
        @Header("Authorization") token: String,
        @Query("customId") customId: Int
    ): List<MessageDTO>

    @POST("/order-processing/customer/custom/send-message")
    suspend fun sendMessageByCustomer(
        @Header("Authorization") token: String,
        @Query("customId") customId: Int,
        @Query("senderId") senderId: Int,
        @Query("text") text: String
    )


}