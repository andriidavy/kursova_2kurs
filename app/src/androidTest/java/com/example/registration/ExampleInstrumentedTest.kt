package com.example.registration

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.registration.database.customer.CustomerApi
import com.example.registration.database.RetrofitService

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
//    @Test
//    fun useAppContext() {
//        // Context of the app under test.
//        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
//        assertEquals("com.example.registration", appContext.packageName)
//    }

    val retrofitService = RetrofitService()
    val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)

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