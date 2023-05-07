//package com.example.registration.database
//
//import androidx.lifecycle.LiveData
//import androidx.room.*
//import com.example.registration.model.users.Customer
//import kotlinx.coroutines.flow.Flow
//
//@Dao
//interface CustomerDAO {
//    @Insert
//    suspend fun insertCustomer(customer: Customer)
//
//    @Update
//    suspend fun updateCustomer(customer: Customer)
//
//    @Delete
//    suspend fun deleteCustomer(customer: Customer)
//
//    @Query("SELECT * FROM customer_data_table")
//    fun getAllCustomers(): Flow<List<Customer>>
//
//
//}