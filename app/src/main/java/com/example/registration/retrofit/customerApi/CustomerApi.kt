package com.example.registration.retrofit.customerApi

import com.example.registration.model.product.Product
import com.example.registration.model.users.Customer
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface CustomerApi {
    @GET("/customer/get-all")
    suspend fun getCustomersAll(): List<Customer>;

    @POST("/customer/save")
    suspend fun save(@Body customer: Customer): Customer;

    @GET("/customer/product/get-all")
    suspend fun getProductsAll(): List<Product>;
}