package com.example.registration.ui.customer.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.registration.R
import com.example.registration.databinding.FragmentProductItemBinding
import com.example.registration.model.product.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
    }

    private fun setupViews() = with(binding) {
        product?.apply {
            productName.text = name
            productDescription.text = description
            productId.text = id.toString()
            productQuantity.text = quantity.toString()
        } ?: showToast(getString(R.string.error_info_product))
    }

    private fun setListeners() = with(binding) {
        buttonAddToCart.setOnClickListener {
            val productId: Int = productId.text.toString().toInt()
            val quantity: Int = countAddToCart.text.toString().toInt()

            lifecycleScope.launch {
                viewModel.addProductToCart(productId, quantity).collect { result ->
                    result.onSuccess { showToast(getString(R.string.success_add_to_cart)) }
                    result.onFailure { showToast(getString(R.string.error_add_to_cart, it)) }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}