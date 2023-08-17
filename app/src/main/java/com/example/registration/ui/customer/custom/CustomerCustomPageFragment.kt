package com.example.registration.ui.customer.custom

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
import com.example.registration.adapter.custom.CustomAdapter
import com.example.registration.databinding.FragmentCustomerCustomPageBinding
import com.example.registration.model.custom.CustomProductDTO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerCustomPageFragment : Fragment() {

    private lateinit var binding: FragmentCustomerCustomPageBinding
    private lateinit var adapter: CustomAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<CustomerCustomPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerCustomPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setObservers()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        adapter = CustomAdapter(emptyList())
        customListRecyclerView.adapter = adapter
        customListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()

        viewModel.getCustomsForCustomer()
    }

    private fun setObservers() {
        viewModel.customDTOArray.observe(viewLifecycleOwner) { customs ->
            adapter.updateCustoms(customs)
        }
    }

    private fun setListeners() {
        adapter.setOnItemClickListener(object : CustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()

                val list: ArrayList<CustomProductDTO>? =
                    viewModel.customDTOArray.value?.getOrNull(position)?.customProductList as ArrayList<CustomProductDTO>?
                bundle.putParcelableArrayList("customProductList", list)

                navController.navigate(
                    R.id.action_customerCustomPageFragment_to_customProductDetailFragment,
                    bundle
                )
            }
        })
    }
}