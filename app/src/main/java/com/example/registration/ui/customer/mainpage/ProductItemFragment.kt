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
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductItemFragment : Fragment() {

    private lateinit var binding: FragmentProductItemBinding
    private val viewModel by viewModels<ProductItemViewModel>()
    val product: Product? = arguments?.getParcelable("product")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        product?.apply {
            productName.text = name
            productDescription.text = description
            productId.text = id.toString()
            productQuantity.text = quantity.toString()
        } ?: viewModel.setMessage("Error getting product information")
    }

    private fun setListeners() = with(binding) {
        buttonAddToCart.setOnClickListener {
            val productId: Int = productId.text.toString().toInt()
            val quantity: Int = countAddToCart.text.toString().toInt()
            viewModel.addProductToCart(productId, quantity)
        }
    }

    private fun setObservers() {
        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}