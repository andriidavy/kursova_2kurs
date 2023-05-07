package com.example.registration.repository

import com.example.registration.model.product.Product
import com.example.registration.retrofit.productApi.ProductApi
import retrofit2.Call

class ProductRepository(
    private val productApi: ProductApi
) {
    fun getProducts(): Call<MutableList<Product>>{
        return productApi.productsAll
    }
}