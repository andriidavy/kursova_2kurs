package com.example.registration.UI.customer.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.UI.login.LoginViewModel
import com.example.registration.adapter.ProductAdapter
import com.example.registration.databinding.FragmentCustomerMainPageBinding
import com.example.registration.model.product.Product
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.customer.CustomerApi
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerMainPageFragment : Fragment() {
    private lateinit var binding: FragmentCustomerMainPageBinding
    private lateinit var adapter: ProductAdapter

    private val viewModel by viewModels<CustomerMainPageViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCustomerMainPageBinding.inflate(inflater)

        adapter = ProductAdapter(emptyList())
        binding.productListRecyclerView.adapter = adapter
        binding.productListRecyclerView.layoutManager = LinearLayoutManager(activity)

        val navController = findNavController()

        viewModel.productsArray.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(products)
        }

        viewModel.getAllProducts()

        adapter.setOnItemClickListener(object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                val product: Product? =
                    viewModel.productsArray.value?.get(position)
                bundle.putParcelable("product", product)

                navController.navigate(
                    R.id.action_customerMainPageFragment_to_productItemFragment,
                    bundle
                )
            }
        })

        binding.buttonToCart.setOnClickListener {
            navController.navigate(R.id.action_customerMainPageFragment_to_customerCartPageFragment)
        }

        binding.buttonToCustoms.setOnClickListener {
            navController.navigate(R.id.action_customerMainPageFragment_to_customerCustomPageFragment)
        }

        binding.buttonToProfile.setOnClickListener {
            navController.navigate(R.id.action_customerMainPageFragment_to_customerProfilePageFragment)
        }

        return binding.root
    }

}