package com.example.registration.ui.customer.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.CartAdapter
import com.example.registration.databinding.FragmentCustomerCartPageBinding
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerCartPageFragment : Fragment() {
    private lateinit var binding: FragmentCustomerCartPageBinding
    private lateinit var adapter: CartAdapter
    private lateinit var navController: NavController

    private val viewModel by viewModels<CustomerCartPageViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()

    private val userId = dataStoreViewModel.getUserId()
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerCartPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CartAdapter(emptyList(), viewModel)
        binding.cartListRecyclerView.adapter = adapter
        binding.cartListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()

        viewModel.getAllCartProducts(userId).observe(viewLifecycleOwner) { cart ->
            adapter.updateCart(cart)
        }

        binding.buttonClearCart.setOnClickListener {
            viewModel.clearCart()
        }

        binding.buttonCreateCustom.setOnClickListener {
            viewModel.createCustom(userId).observe(viewLifecycleOwner) { customId ->
                if (customId != null) {
                    val bundle = Bundle()
                    bundle.putInt("customId", customId)
                    navController.navigate(
                        R.id.action_customerCartPageFragment_to_addDepartForNewCustomFragment,
                        bundle
                    )
                }
            }
        }

        viewModel.message.observe(
            viewLifecycleOwner
        )
        { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }

    }

}