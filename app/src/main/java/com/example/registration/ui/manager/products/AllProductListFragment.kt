package com.example.registration.ui.manager.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ProductAdapter
import com.example.registration.databinding.FragmentAllProductListBinding
import com.example.registration.global.ToastObj
import com.example.registration.model.product.ProductDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllProductListFragment : Fragment() {

    private lateinit var binding: FragmentAllProductListBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<AllProductListViewModel>()
    private var searchStr = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllProductListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
        setListeners()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(300)
            viewModel.getAllProductsPage(0)
            viewModel.currentPage = 0
            updatePageInfo(viewModel.currentPage)
        }
    }

    private fun setViews() = with(binding) {
        adapter = ProductAdapter(emptyList(), onItemClick())
        allProductListRecyclerView.adapter = adapter
        allProductListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
        etSearchProductField.doOnTextChanged { text, _, _, _ ->
            if (text.toString().isNotBlank()) {
                searchStr = text.toString()
            } else {
                searchStr = ""
            }
        }
        updatePageInfo(viewModel.currentPage)
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productDTOArray.collect { products ->
                    adapter.updateProducts(products)
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        buttonAddProduct.setOnClickListener {
            navController.navigate(R.id.action_allProductListFragment_to_addProductFragment)
        }

        buttonSearchProduct.setOnClickListener {
            val searchStr: String = searchStr
            if (searchStr.isBlank()) viewModel.getAllProductsPage(0) else viewModel.getProductsBySearch(searchStr, 0, 0)
            viewModel.currentPage = 0
            updatePageInfo(viewModel.currentPage)
        }

        btNext.setOnClickListener {
            if (searchStr.isBlank()) {
                if (viewModel.isLastPage()) {
                    ToastObj.longToastMake("Остання сторінка", context)
                } else {
                    viewModel.loadNextPage()
                    updatePageInfo(viewModel.currentPage)
                }
            } else {
                if (viewModel.isLastPage()) {
                    ToastObj.longToastMake("Остання сторінка", context)
                } else {
                    viewModel.loadNextPageBySearch()
                    updatePageInfo(viewModel.currentPage)
                }
            }
        }
        btPrevious.setOnClickListener {
            if (searchStr.isBlank()) {
                viewModel.loadPreviousPage()
                updatePageInfo(viewModel.currentPage)
            }else {
                viewModel.loadPreviousPageBySearch()
                updatePageInfo(viewModel.currentPage)
            }
        }
    }

    private fun updatePageInfo(page: Int) = with(binding) {
        val from = page * 10 + 1
        lifecycleScope.launch {
            delay(600)
            val to = from + viewModel.productDTOArray.value.size - 1
            tvPageInfo.text = "з $from по $to"
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val product: ProductDTO = viewModel.productDTOArray.value[position]
            bundle.putParcelable("updProduct", product)
            navController.navigate(
                R.id.action_allProductListFragment_to_updateProductFragment,
                bundle
            )
        }
    }
}