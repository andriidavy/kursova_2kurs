package com.example.registration.retrofit.customerApi;

import com.example.registration.model.users.Customer;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CustomerApi {
    @GET("/customer/get-all")
    Call<List<Customer>> getCustomersAll();

    @POST("/customer/save")
    Call<Customer> save(@Body Customer customer);
}
