package com.example.registration.ui.customer.custom

import android.content.Context
import android.content.SharedPreferences
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
import com.example.registration.adapter.custom.CustomAdapter
import com.example.registration.databinding.FragmentCustomerCustomPageBinding
import com.example.registration.model.custom.CustomProductDTO
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.customer.CustomerApi

class CustomerCustomPageFragment : Fragment() {
    private lateinit var binding: FragmentCustomerCustomPageBinding
    private lateinit var viewModel: CustomerCustomPageViewModel
    private lateinit var adapter: CustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerCustomPageBinding.inflate(inflater)

        adapter = CustomAdapter(emptyList())
        binding.customListRecyclerView.adapter = adapter
        binding.customListRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)
        val customerRepository = CustomerRepository(customerApi)
        val viewModelFactory =
            CustomerCustomPageViewModelFactory(customerRepository)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[CustomerCustomPageViewModel::class.java]

        binding.lifecycleOwner = this

        val navController = findNavController()

        viewModel.customDTOArray.observe(viewLifecycleOwner) { customs ->
            adapter.updateCustoms(customs)
        }

        val sharedCustomerIdPreferences: SharedPreferences =
            requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedCustomerIdPreferences)

        viewModel.getCustomsForCustomer()


        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //test
                Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                val list: ArrayList<CustomProductDTO>? =
                    viewModel.customDTOArray.value?.get(position)?.customProductList as ArrayList<CustomProductDTO>?
                bundle.putParcelableArrayList("customProductList", list)
                navController.navigate(R.id.action_customerCustomPageFragment_to_customProductDetailFragment, bundle)
            }
        })

        return binding.root
    }

}