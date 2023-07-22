package com.example.registration.ui.customer.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.registration.databinding.FragmentProductItemBinding
import com.example.registration.model.product.Product
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductItemFragment : Fragment() {
    private lateinit var binding: FragmentProductItemBinding

    private val viewModel by viewModels<ProductItemViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductItemBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setListeners()
        setObservers()
    }

    private fun setupViews() = with(binding) {
        @Suppress("DEPRECATION")
        val product: Product? =
            arguments?.getParcelable("product")

        productName.text = product?.name
        productDescription.text = product?.description
        productId.text = product?.id.toString()
        productQuantity.text = product?.quantity.toString()
    }

    private fun setListeners() = with(binding) {
        buttonAddToCart.setOnClickListener {
            val productId: Int = Integer.parseInt(productId.text.toString())
            val quantity: Int = Integer.parseInt(countAddToCart.text.toString())
            viewModel.addProductToCart(productId, quantity)
        }
    }
    private fun setObservers() {
        viewModel.message.observe(
            viewLifecycleOwner
        )
        { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
    }
}