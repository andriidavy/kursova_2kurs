package com.example.registration.myISAM.ui.customer.productsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.registration.R
import com.example.registration.databinding.FragmentProductItemBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.product.ProductDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiProductItemFragment : Fragment() {

    private lateinit var binding: FragmentProductItemBinding
    private val viewModel by viewModels<MiProductItemViewModel>()

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

        val productDTO: ProductDTO? = arguments?.getParcelable("product")
        productDTO?.apply {
            productName.text = name
            productDescription.text = description
            productId.text = id.toString()
            productQuantity.text = quantity.toString()
            productPrice.text = price.toString()
        }
    }

    private fun setListeners() = with(binding) {
        buttonAddToCart.setOnClickListener {
            val productId: Int = productId.text.toString().toInt()
            val quantity: Int = countAddToCart.text.toString().toInt()

            lifecycleScope.launch {
                viewModel.addProductToCart(productId, quantity).collect { result ->
                    result.onSuccess {
                        ToastObj.shortToastMake(
                            getString(R.string.success_add_to_cart),
                            context
                        )
                    }
                    result.onFailure {
                        ToastObj.shortToastMake(
                            getString(
                                R.string.error_add_to_cart,
                                it
                            ), context
                        )
                    }
                }
            }
        }
    }
}