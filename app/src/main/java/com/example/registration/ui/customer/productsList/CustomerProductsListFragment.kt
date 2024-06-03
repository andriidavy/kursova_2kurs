package com.example.registration.ui.customer.productsList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomerProductsListFragment : Fragment() {
    private lateinit var binding: FragmentCustomerProductsListBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<CustomerProductsListViewModel>()
    private var num: Int = -1
    private var isChecked: Boolean = false
    private var actualSearchStr: String = ""
    private var actualPage: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerProductsListBinding.inflate(inflater)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setObservers()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
            viewModel.getCartCount()
    }

    // Inflate the menu for this fragment
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_customer_products_list, menu)
        val menuItem = menu.findItem(R.id.action_cart)
        val actionView = menuItem.actionView

        val cartBadge = actionView?.findViewById<TextView>(R.id.cart_badge)

        lifecycleScope.launch {
            viewModel.cartCount.collect { count ->
                if (count > 0) {
                    if (cartBadge != null) {
                        cartBadge.visibility = View.VISIBLE
                    }
                    if (cartBadge != null) {
                        cartBadge.text = count.toString()
                    }
                } else {
                    if (cartBadge != null) {
                        cartBadge.visibility = View.GONE
                    }
                }
            }
        }

        actionView?.setOnClickListener {
            onOptionsItemSelected(menuItem)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    // Handle the cart button click
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                navController.navigate(R.id.action_customerProductsListFragment_to_customerCartPageFragment)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
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

                launch {
                    viewModel.productsArray.collect { products ->
                        adapter.updateProducts(products)
                    }
                }
                launch {
                    viewModel.currentPage.collect { page ->
                        actualPage = page
                        updatePageInfo(actualPage)
                    }
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
            actualSearchStr = etSearchField.text.toString()
            if (actualSearchStr.isBlank()) {
                viewModel.getAllProductsPage(0)
                updatePageInfo(actualPage)
                return@setOnClickListener
            }
            if (isChecked) {
                val minPrice: Double =
                    etMinRange.text.toString().toDoubleOrNull() ?: viewModel.minPrice.value
                val maxPrice: Double =
                    etMaxRange.text.toString().toDoubleOrNull() ?: viewModel.maxPrice.value

                viewModel.getProductsBySearchWithPriceRange(
                    actualSearchStr,
                    num,
                    minPrice,
                    maxPrice,
                    0
                )
                updatePageInfo(actualPage)
            } else {
                viewModel.getProductsBySearch(actualSearchStr, num, 0)
                updatePageInfo(actualPage)
            }
        }

        btNext.setOnClickListener {
            if (actualSearchStr.isBlank()) {
                if (viewModel.isLastProductsList()) {
                    ToastObj.longToastMake("Остання сторінка", context)
                } else {
                    viewModel.loadNextPage()
                    updatePageInfo(actualPage)
                }
                return@setOnClickListener
            }
            if (isChecked) {
                if (viewModel.isLastProductsList()) {
                    ToastObj.longToastMake("Остання сторінка", context)
                } else {
                    viewModel.loadNextPageBySearchWithPrice()
                    updatePageInfo(actualPage)
                }
            } else {
                if (viewModel.isLastProductsList()) {
                    ToastObj.longToastMake("Остання сторінка", context)
                } else {
                    viewModel.loadNextPageBySearch()
                    updatePageInfo(actualPage)
                }
            }
        }
        btPrevious.setOnClickListener {
            if (actualSearchStr.isBlank()) {
                viewModel.loadPreviousPage()
                updatePageInfo(actualPage)
                return@setOnClickListener
            }
            if (isChecked) {
                viewModel.loadPreviousPageBySearchWithPrice()
                updatePageInfo(actualPage)
            } else {
                viewModel.loadPreviousPageBySearch()
                updatePageInfo(actualPage)
            }
        }
    }

    private fun updatePageInfo(page: Int) = with(binding) {
        val from = page * 10 + 1
        lifecycleScope.launch {
            delay(500)
            val to = from + viewModel.productsArray.value.size - 1
            tvPageInfo.text = "з $from по $to"
        }
    }

    private fun itemClicked(): (Int) -> Unit {
        return { position ->
            val product = viewModel.productsArray.value.getOrNull(position)
            product?.let {
                val bundle = Bundle()
                bundle.putParcelable("product", product)
                navController.navigate(
                    R.id.action_customerProductsListFragment_to_productItemFragment,
                    bundle
                )
            } ?: ToastObj.shortToastMake(getString(R.string.error_info_product), context)
        }
    }
}