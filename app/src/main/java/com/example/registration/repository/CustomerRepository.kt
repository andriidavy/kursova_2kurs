package com.example.registration.repository

import com.example.registration.model.product.Product
import com.example.registration.retrofit.customerApi.CustomerApi
import com.example.registration.model.users.Customer
import retrofit2.Call

class CustomerRepository(
//    private val dao: CustomerDAO
    private val customerApi: CustomerApi
) {

    suspend fun save(customer: Customer): Customer {
        return customerApi.save(customer)
    }

    suspend fun getCustomers(): List<Customer> {
        return customerApi.getCustomersAll()
    }

    suspend fun getProducts(): List<Product>{
        return customerApi.getProductsAll()
    }
}













//ROOM DATABASE REALIZATION
//import androidx.lifecycle.LiveData
//import com.example.registration.database.CustomerDAO
//import com.example.registration.model.users.Customer
//import kotlinx.coroutines.flow.Flow

//val customers: Flow<List<Customer>> get() =  dao.getAllCustomers()
//
//    suspend fun insert(customer: Customer){
//        return dao.insertCustomer(customer)
//    }
//
//    suspend fun update(customer: Customer){
//        return dao.updateCustomer(customer)
//    }
//
//    suspend fun delete(customer: Customer){
//        return dao.deleteCustomer(customer)
//    }