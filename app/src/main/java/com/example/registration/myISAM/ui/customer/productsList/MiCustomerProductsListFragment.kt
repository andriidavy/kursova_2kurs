package com.example.registration.myISAM.ui.customer.productsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ProductAdapter
import com.example.registration.databinding.FragmentCustomerProductsListBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiCustomerProductsListFragment : Fragment() {
    private lateinit var binding: FragmentCustomerProductsListBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<MiCustomerProductsListViewModel>()
    private var num: Int = -1
    private var isChecked: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerProductsListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setObservers()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        adapter = ProductAdapter(emptyList(), itemClicked())
        productListRecyclerView.adapter = adapter
        productListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()

        //set Spinner
        val types = arrayOf("Звичайний", "Суміжний", "Розширений")
        val spinner = spinnerChooseSearchType
        val arrayAdapter =
            activity?.let { ArrayAdapter(it, android.R.layout.simple_spinner_item, types) }

        spinner.apply {
            adapter = arrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    num = position
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Code to perform some action when nothing is selected
                }
            }

            etSearchField.doOnTextChanged { text, _, _, _ ->
                if (text.toString().isBlank()) {
                    cbRangeOption.isEnabled = false
                    cbRangeOption.alpha = 0.5f
                } else {
                    cbRangeOption.isEnabled = true
                    cbRangeOption.alpha = 1f
                }
            }
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productsArray.collect { products ->
                    adapter.updateProducts(products)
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        cbRangeOption.setOnClickListener {
            isChecked = (it as CheckBox).isChecked

            if (isChecked) {
                etMinRange.visibility = View.VISIBLE
                etMaxRange.visibility = View.VISIBLE
            } else {
                etMinRange.visibility = View.GONE
                etMaxRange.visibility = View.GONE
            }
        }

        buttonSearch.setOnClickListener {
            val searchStr = etSearchField.text.toString()
            if (searchStr.isBlank()) {
                viewModel.getAllProducts()
                return@setOnClickListener
            }
            if (isChecked) {
                val minPrice: Double =
                    etMinRange.text.toString().toDoubleOrNull() ?: viewModel.minPrice.value
                val maxPrice: Double =
                    etMaxRange.text.toString().toDoubleOrNull() ?: viewModel.maxPrice.value

                viewModel.getProductsBySearchWithPriceRange(searchStr, num, minPrice, maxPrice)
            } else {
                viewModel.getProductsBySearch(searchStr, num)
            }
        }
    }

    private fun itemClicked(): (Int) -> Unit {
        return { position ->
            val product = viewModel.productsArray.value.getOrNull(position)
            product?.let {
                val bundle = Bundle()
                bundle.putParcelable("product", product)
                navController.navigate(
                    R.id.action_miCustomerProductsListFragment_to_miProductItemFragment,
                    bundle
                )
            } ?: ToastObj.shortToastMake(getString(R.string.error_info_product), context)
        }
    }
}