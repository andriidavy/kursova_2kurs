package com.example.registration.ui.manager.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.set
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.databinding.FragmentAllProductListBinding
import com.example.registration.databinding.FragmentUpdateProductBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.custom.CustomProductDTO
import com.example.registration.model.product.ProductDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpdateProductFragment : Fragment() {
    private lateinit var binding: FragmentUpdateProductBinding
    private val viewModel by viewModels<UpdateProductViewModel>()
    private lateinit var navController: NavController
    private var actualProductId = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUpdateProductBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
    }

    private fun setViews() = with(binding) {
        navController = findNavController()
        val product: ProductDTO? =
            arguments?.getParcelable("updProduct")
        product?.let { resultProduct ->
            actualProductId = resultProduct.id
            etName.setText(resultProduct.name)
            etDesc.setText(resultProduct.description)
            etQuantity.setText(resultProduct.quantity.toString())
            etPrice.setText(resultProduct.price.toString())
        }
    }

    private fun setListeners() = with(binding) {
        btConfirm.setOnClickListener {
            val actualName = etName.text.toString()
            val actualDesc = etDesc.text.toString()
            val actualQuantity = etQuantity.text.toString().toInt()
            val actualPrice = etPrice.text.toString().toDouble()
            lifecycleScope.launch {
                viewModel.updateProduct(
                    actualProductId,
                    actualName,
                    actualDesc,
                    actualQuantity,
                    actualPrice
                ).collect { result ->
                    result.onSuccess {
                        ToastObj.longToastMake("Продукт оновлено!", context)
                        navController.navigate(R.id.action_updateProductFragment_to_allProductListFragment)
                    }
                    result.onFailure {
                        ToastObj.longToastMake(
                            "Помилка оновлення продукту!",
                            context
                        )
                    }
                }
            }
        }
    }
}