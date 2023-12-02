package com.example.registration

import com.example.registration.database.customer.CustomerApi
import com.example.registration.database.RetrofitService
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }
val retrofitService = RetrofitService()
    val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)
//    @Test
//    fun printAll(){
//        val retrofitService = RetrofitService()
//        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)
//        val list = customerApi.customersAll
//        println(list)
//    }
    @Test
    fun addCustomer(){
        val name: String = "Andy"
        val surname: String = "Davy"
        val email: String = "and@gmail.com"
        val password: String ="and123"
        val customer = Customer(
            name,
            surname,
            email,
            password
        )
        customerApi.save(customer)
    }
}