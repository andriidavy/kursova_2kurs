package com.example.registration.ui.manager.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.registration.R
import com.example.registration.databinding.FragmentAddProductBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddProductFragment : Fragment() {

    private lateinit var binding: FragmentAddProductBinding
    private val viewModel by viewModels<AddProductViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }


    private fun setListeners() = with(binding) {
        addProductButton.setOnClickListener {
            lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isProductExists(productName.text.toString())
                        .collect { result ->
                            result.onSuccess { isExist ->
                                if (!isExist) {
                                    val name: String = productName.text.toString()
                                    val quantity: Int = if (productQuantity.text.isNotEmpty()) {
                                        Integer.parseInt(productQuantity.text.toString())
                                    } else {
                                        0
                                    }
                                    val description: String = productDescription.text.toString()
                                    val price: Double? =
                                        productPrice.text.toString().toDoubleOrNull()

                                    if (name.isNotEmpty() && quantity > 0 && price != null) {
                                        lifecycleScope.launch {
                                            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                                                viewModel.provideProduct(
                                                    name,
                                                    quantity,
                                                    price,
                                                    description
                                                ).collect { result ->
                                                    result.onSuccess { productId ->
                                                        ToastObj.longToastMake(
                                                            getString(
                                                                R.string.success_add_product,
                                                                productId
                                                            ),
                                                            context
                                                        )
                                                    }
                                                    result.onFailure {
                                                        ToastObj.shortToastMake(
                                                            getString(R.string.add_product_error),
                                                            context
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    } else {
                                        ToastObj.shortToastMake(
                                            getString(R.string.add_product_null_check),
                                            context
                                        )
                                    }
                                } else {
                                    ToastObj.shortToastMake("Такий товар вже існує!", context)
                                }
                            }
                        }
                }
            }
        }
    }
}