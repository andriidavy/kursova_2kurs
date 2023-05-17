package com.example.registration.fragment.customerFragments.cart

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.registration.databinding.ListCartProductItemBinding
import com.example.registration.repository.CustomerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.customerApi.CustomerApi
import com.example.registration.viewmodel.customer.cart.CustomerCartItemViewModel
import com.example.registration.viewmodel.customer.cart.CustomerCartItemViewModelFactory
import com.example.registration.viewmodel.customer.cart.CustomerCartPageViewModel
import com.example.registration.viewmodel.customer.cart.CustomerCartPageViewModelFactory

class CartProductFragment : Fragment() {
    private lateinit var binding: ListCartProductItemBinding
    private lateinit var viewModel: CustomerCartItemViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListCartProductItemBinding.inflate(inflater)

        val retrofitService = RetrofitService()

        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)

        val customerRepository = CustomerRepository(customerApi)

        val viewModelFactory =
            CustomerCartItemViewModelFactory(customerRepository)

        viewModel = ViewModelProvider(this, viewModelFactory)[CustomerCartItemViewModel::class.java]

        val sharedCustomerIdPreferences: SharedPreferences = requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedCustomerIdPreferences)

        binding.buttonDeleteItem.setOnClickListener {
            val productId: Int = Integer.parseInt(binding.idForProduct.text.toString())
            viewModel.removeProductFromCart(productId)
            Toast.makeText(requireContext(), "Item delete", Toast.LENGTH_SHORT).show()
        }
        return binding.root
    }
}