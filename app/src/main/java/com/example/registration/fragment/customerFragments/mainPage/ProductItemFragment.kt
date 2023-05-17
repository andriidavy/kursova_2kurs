package com.example.registration.fragment.customerFragments.mainPage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.registration.databinding.FragmentProductItemBinding
import com.example.registration.repository.CustomerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.customerApi.CustomerApi
import com.example.registration.viewmodel.customer.CustomerMainPageViewModel
import com.example.registration.viewmodel.customer.CustomerMainPageViewModelFactory
import com.example.registration.viewmodel.customer.CustomerProductDetailViewModel
import com.example.registration.viewmodel.customer.CustomerProductDetailViewModelFactory

class ProductItemFragment : Fragment() {
    private lateinit var binding: FragmentProductItemBinding
    private lateinit var viewModel: CustomerProductDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductItemBinding.inflate(inflater)

        val retrofitService = RetrofitService()

        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)

        val customerRepository = CustomerRepository(customerApi)

        val viewModelFactory =
            CustomerProductDetailViewModelFactory(customerRepository)

        viewModel = ViewModelProvider(this, viewModelFactory)[CustomerProductDetailViewModel::class.java]

        val sharedCustomerIdPreferences: SharedPreferences = requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        val customerId: Int = sharedCustomerIdPreferences.getInt("customerId",0)

        binding.buttonAddToCart.setOnClickListener {
            val productId: Int = Integer.parseInt(binding.productId.text.toString())
            val quantity: Int = Integer.parseInt(binding.countAddToCart.text.toString())
            viewModel.addProductToCart(customerId, productId, quantity)
        }


        binding.productName.text = arguments?.getString("name_product")
        binding.productDescription.text = arguments?.getString("description_product")
        binding.productId.text = arguments?.getInt("id_product").toString()
        binding.productQuantity.text = arguments?.getInt("quantity_product").toString()

        return binding.root
    }

}