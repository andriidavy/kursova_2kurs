package com.example.registration.ui.customer.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ProductAdapter
import com.example.registration.databinding.FragmentCustomerMainPageBinding
import com.example.registration.model.product.Product
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerMainPageFragment : Fragment() {
    private lateinit var binding: FragmentCustomerMainPageBinding
    private lateinit var adapter: ProductAdapter
    private lateinit var navController: NavController
    private lateinit var productList: List<Product>

    private val viewModel by viewModels<CustomerMainPageViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerMainPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setObservers()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        adapter = ProductAdapter(emptyList())
        productListRecyclerView.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(activity)
        }
        navController = findNavController()
    }

    private fun setObservers() {
        viewModel.getAllProducts().observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(products)
            productList = products
        }
    }

    private fun setListeners() = with(binding) {
        adapter.setOnItemClickListener(object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                val product: Product =
                    productList[position]
                bundle.putParcelable("product", product)

                navController.navigate(
                    R.id.action_customerMainPageFragment_to_productItemFragment,
                    bundle
                )
            }
        })

        buttonToCart.setOnClickListener {
            navController.navigate(R.id.action_customerMainPageFragment_to_customerCartPageFragment)
        }

        buttonToCustoms.setOnClickListener {
            navController.navigate(R.id.action_customerMainPageFragment_to_customerCustomPageFragment)
        }

        buttonToProfile.setOnClickListener {
            navController.navigate(R.id.action_customerMainPageFragment_to_customerProfilePageFragment)
        }
    }
}