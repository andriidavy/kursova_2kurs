package com.example.registration.ui.manager.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.registration.R
import com.example.registration.databinding.FragmentAddProductBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
            val name: String = productName.text.toString()
            val quantity: Int = if (productQuantity.text.isNotEmpty()) {
                Integer.parseInt(productQuantity.text.toString())
            } else { 0 }
            val description: String = productDescription.text.toString()

            if (name.isNotEmpty() && quantity > 0) {
                lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.saveProduct(name, description, quantity)
                    withContext(Dispatchers.Main){
                        ToastObj.shortToastMake(getString(R.string.success_add_product),context)
                    }
                }
            } else {
                ToastObj.shortToastMake(getString(R.string.add_product_null_check), context)
            }
        }
    }
}