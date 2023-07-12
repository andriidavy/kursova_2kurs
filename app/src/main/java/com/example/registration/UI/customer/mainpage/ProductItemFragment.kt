package com.example.registration.UI.customer.mainpage

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.registration.databinding.FragmentProductItemBinding
import com.example.registration.model.product.Product
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.customer.CustomerApi
import com.example.registration.datastore.DataStoreViewModel

class ProductItemFragment : Fragment() {
    private lateinit var binding: FragmentProductItemBinding

    private val viewModel by viewModels<CustomerProductDetailViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductItemBinding.inflate(inflater)

        //get customer id from datastore
        val customerId = dataStoreViewModel.getUserId()

        binding.buttonAddToCart.setOnClickListener {
            val productId: Int = Integer.parseInt(binding.productId.text.toString())
            val quantity: Int = Integer.parseInt(binding.countAddToCart.text.toString())
            viewModel.addProductToCart(customerId, productId, quantity)
        }

        val product: Product? =
            arguments?.getParcelable("product")

        binding.productName.text = product?.name
        binding.productDescription.text = product?.description
        binding.productId.text = product?.id.toString()
        binding.productQuantity.text = product?.quantity.toString()

        viewModel.message.observe(
            viewLifecycleOwner
        )
        { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

        return binding.root
    }

}