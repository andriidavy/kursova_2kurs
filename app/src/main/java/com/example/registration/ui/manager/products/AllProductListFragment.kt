package com.example.registration.ui.manager.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllProductListFragment : Fragment() {

    private lateinit var binding: FragmentAllProductListBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<AllProductListViewModel>()

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

    private fun setViews() = with(binding) {
        adapter = ProductAdapter(emptyList(), onItemClick())
        allProductListRecyclerView.adapter = adapter
        allProductListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productArray.collect { products ->
                    adapter.updateProducts(products)
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        buttonAddProduct.setOnClickListener {
            navController.navigate(R.id.action_allProductListFragment_to_addProductFragment)
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            ToastObj.shortToastMake("Clicked on item $position", context)
        }
    }
}