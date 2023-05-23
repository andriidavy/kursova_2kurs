package com.example.registration.fragment.managerFragments.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ProductAdapter
import com.example.registration.databinding.FragmentAllProductListBinding
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.products.AllProductListViewModel
import com.example.registration.viewmodel.manager.products.AllProductListViewModelFactory


class allProductListFragment : Fragment() {
    private lateinit var binding: FragmentAllProductListBinding
    private lateinit var viewModel: AllProductListViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllProductListBinding.inflate(inflater)


        adapter = ProductAdapter(emptyList())
        binding.allProductListRecyclerView.adapter = adapter
        binding.allProductListRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            AllProductListViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[AllProductListViewModel::class.java]

        val navController = findNavController()

        viewModel.productArray.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(products)
        }

        viewModel.getAllProducts()

        binding.buttonAddProduct.setOnClickListener {
            navController.navigate(R.id.action_allProductListFragment_to_addProductFragment)
        }

        adapter.setOnItemClickListener(object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
            }
        })

        return binding.root
    }
}