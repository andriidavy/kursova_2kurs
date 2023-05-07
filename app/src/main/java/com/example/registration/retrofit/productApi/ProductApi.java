package com.example.registration.retrofit.productApi;

import com.example.registration.model.product.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductApi {
    @GET("/product/get-all")
    Call<List<Product>> getProductsAll();
}
