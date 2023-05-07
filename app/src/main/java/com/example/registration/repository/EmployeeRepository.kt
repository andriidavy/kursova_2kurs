package com.example.registration.repository

import com.example.registration.model.users.Customer
import com.example.registration.model.users.Employee
import com.example.registration.retrofit.employeeApi.EmployeeApi
import retrofit2.Call

class EmployeeRepository(private val employeeApi: EmployeeApi) {

    fun getEmployees(): Call<MutableList<Employee>> {
        return employeeApi.employeeAll
    }
}