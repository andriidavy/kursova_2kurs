package com.example.registration.retrofit.employeeApi;

import com.example.registration.model.users.Employee;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EmployeeApi {
    @GET("/employee/get-all")
    Call<List<Employee>> getEmployeeAll();
}
