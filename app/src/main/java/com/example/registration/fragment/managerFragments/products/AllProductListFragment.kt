package com.example.registration.fragment.managerFragments.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ProductAdapter
import com.example.registration.databinding.FragmentAllProductListBinding
import com.example.registration.viewmodel.manager.products.AllProductListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllProductListFragment : Fragment() {

    private lateinit var binding: FragmentAllProductListBinding
    private lateinit var adapter: ProductAdapter
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
        adapter = ProductAdapter(emptyList(), onItemClick())
        binding.allProductListRecyclerView.adapter = adapter
        binding.allProductListRecyclerView.layoutManager = LinearLayoutManager(activity)

        val navController = findNavController()

        viewModel.productArray.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(products)
        }

        binding.buttonAddProduct.setOnClickListener {
            navController.navigate(R.id.action_allProductListFragment_to_addProductFragment)
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            Toast.makeText(
                activity,
                "Clicked on item $position",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}