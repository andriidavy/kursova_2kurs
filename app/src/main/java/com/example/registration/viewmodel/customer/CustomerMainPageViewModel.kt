package com.example.registration.viewmodel.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registration.model.product.Product
import com.example.registration.repository.CustomerRepository
import com.example.registration.repository.ProductRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerMainPageViewModel(
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _productsArray = MutableLiveData<List<Product>>()
    val productsArray: LiveData<List<Product>>
        get() = _productsArray

    fun getAllProducts(): LiveData<List<Product>> {
        productRepository.getProducts().enqueue(object :
            Callback<MutableList<Product>> {
            override fun onResponse(
                call: Call<MutableList<Product>>,
                response: Response<MutableList<Product>>
            ) {
                _productsArray.postValue(response.body())
            }

            override fun onFailure(call: Call<MutableList<Product>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return productsArray
    }
}