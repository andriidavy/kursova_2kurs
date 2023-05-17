package com.example.registration.fragment.customerFragments.mainPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.ProductAdapter
import com.example.registration.databinding.FragmentCustomerMainPageBinding
import com.example.registration.repository.CustomerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.customerApi.CustomerApi
import com.example.registration.viewmodel.customer.CustomerMainPageViewModel
import com.example.registration.viewmodel.customer.CustomerMainPageViewModelFactory


class CustomerMainPageFragment : Fragment() {
    private lateinit var binding: FragmentCustomerMainPageBinding
    private lateinit var viewModel: CustomerMainPageViewModel
    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCustomerMainPageBinding.inflate(inflater)

        adapter = ProductAdapter(emptyList())
        binding.productListRecyclerView.adapter = adapter
        binding.productListRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()

        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)

        val customerRepository = CustomerRepository(customerApi)

        val viewModelFactory =
            CustomerMainPageViewModelFactory(customerRepository)

        viewModel = ViewModelProvider(this, viewModelFactory)[CustomerMainPageViewModel::class.java]

        binding.customerMainPageViewModel = viewModel
        binding.lifecycleOwner = this

        val navController = findNavController()

        viewModel.productsArray.observe(viewLifecycleOwner) { products ->
            adapter.updateProducts(products)
        }

        viewModel.getAllProducts()

        adapter.setOnItemClickListener(object : ProductAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                viewModel.productsArray.value?.get(position)?.id?.let {
                    bundle.putInt(
                        "id_product",
                        it
                    )
                }
                viewModel.productsArray.value?.get(position)?.quantity?.let {
                    bundle.putInt("quantity_product", it)
                }
                bundle.putString("name_product", viewModel.productsArray.value?.get(position)?.name)
                bundle.putString(
                    "description_product",
                    viewModel.productsArray.value?.get(position)?.description
                )
                navController.navigate(
                    R.id.action_customerMainPageFragment_to_productItemFragment,
                    bundle
                )
            }
        })

        binding.buttonToCart.setOnClickListener {
            navController.navigate(R.id.action_customerMainPageFragment_to_customerCartPageFragment)
        }

        return binding.root
    }

}