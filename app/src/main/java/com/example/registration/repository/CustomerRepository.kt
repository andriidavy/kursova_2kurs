package com.example.registration.repository

import com.example.registration.model.cart.CartProductDTO
import com.example.registration.model.custom.CustomDTO
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.model.product.Product
import com.example.registration.retrofit.customerApi.CustomerApi
import com.example.registration.model.users.Customer
import com.example.registration.model.users.CustomerProfileDTO
import retrofit2.http.Body
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

    suspend fun getCustomerProfileById(customerId: Int): CustomerProfileDTO{
        return customerApi.getCustomerProfileById(customerId)
    }

    suspend fun getProducts(): List<Product>{
        return customerApi.getProductsAll()
    }

    suspend fun getCartProducts(customerId: Int): List<CartProductDTO>{
        return customerApi.getCartProducts(customerId)
    }

    suspend fun getCustomsForCustomer(customerId: Int): List<CustomDTO>{
        return customerApi.getCustomsForCustomer(customerId)
    }

    suspend fun addProductToCart(customerId: Int, productId: Int, quantity: Int){
        return customerApi.addProductToCart(customerId, productId, quantity)
    }

    suspend fun createCustom(customerId: Int) : Int{
        return customerApi.createCustom(customerId)
    }

    suspend fun removeProductFromCart(customerId: Int, productId: Int){
        return customerApi.removeProductFromCart(customerId, productId)
    }

    suspend fun clearCart(customerId: Int){
        return customerApi.clearCart(customerId)
    }

    suspend fun assignDepartmentToCustom(customId: Int, departmentId: Int){
        return customerApi.assignDepartmentToCustom(customId, departmentId)
    }

    suspend fun  getAllDepartments() : List<DepartmentDTO>{
        return customerApi.getAllDepartments()
    }
}