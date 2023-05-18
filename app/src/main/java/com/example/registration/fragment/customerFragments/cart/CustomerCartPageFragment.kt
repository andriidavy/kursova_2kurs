package com.example.registration.fragment.customerFragments.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.CartAdapter
import com.example.registration.databinding.FragmentCustomerCartPageBinding
import com.example.registration.repository.CustomerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.customerApi.CustomerApi
import com.example.registration.viewmodel.customer.cart.CustomerCartPageViewModel
import com.example.registration.viewmodel.customer.cart.CustomerCartPageViewModelFactory

class CustomerCartPageFragment : Fragment() {
    private lateinit var binding: FragmentCustomerCartPageBinding
    private lateinit var viewModel: CustomerCartPageViewModel
    private lateinit var adapter: CartAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCustomerCartPageBinding.inflate(inflater)
        adapter = CartAdapter(emptyList())
        binding.cartListRecyclerView.adapter = adapter
        binding.cartListRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)
        val customerRepository = CustomerRepository(customerApi)
        val viewModelFactory =
            CustomerCartPageViewModelFactory(customerRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CustomerCartPageViewModel::class.java]

        binding.lifecycleOwner = this

        viewModel.cartProductsArrayDTO.observe(viewLifecycleOwner) { cart ->
            adapter.updateCart(cart)
        }

        val sharedCustomerIdPreferences: SharedPreferences = requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedCustomerIdPreferences)

        viewModel.getAllCartProducts()

        binding.buttonClearCart.setOnClickListener {
            viewModel.clearCart()
        }

        binding.buttonCreateCustom.setOnClickListener {
            viewModel.createCustom()
        }

        viewModel.message.observe(
            viewLifecycleOwner
        )
        { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

        return binding.root
    }

}