package com.example.registration.repository

import com.example.registration.model.cart.CartProduct
import com.example.registration.model.product.Product
import com.example.registration.retrofit.customerApi.CustomerApi
import com.example.registration.model.users.Customer
import retrofit2.Call
import retrofit2.http.Query

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

    suspend fun getCartProducts(customerId: Int): List<CartProduct>{
        return customerApi.getCartProducts(customerId)
    }

    suspend fun addProductToCart(customerId: Int, productId: Int, quantity: Int){
        return customerApi.addProductToCart(customerId, productId, quantity)
    }

    suspend fun removeProductFromCart(customerId: Int, productId: Int){
        return customerApi.removeProductFromCart(customerId, productId)
    }

    suspend fun clearCart(customerId: Int){
        return customerApi.clearCart(customerId)
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