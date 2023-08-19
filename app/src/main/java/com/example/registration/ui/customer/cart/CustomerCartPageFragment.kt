package com.example.registration.ui.customer.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.CartAdapter
import com.example.registration.databinding.FragmentCustomerCartPageBinding
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CustomerCartPageFragment : Fragment() {

    private lateinit var binding: FragmentCustomerCartPageBinding
    private lateinit var adapter: CartAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<CustomerCartPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerCartPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setObservers()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        adapter = CartAdapter(emptyList(), itemRemovedClick())
        cartListRecyclerView.adapter = adapter
        cartListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cartProductsArrayDTO.collect { cart ->
                    adapter.updateCart(cart)
                }
            }
        }

        viewModel.message.observe(
            viewLifecycleOwner
        )
        { message -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show() }
    }

    private fun setListeners() = with(binding) {
        buttonCreateCustom.setOnClickListener {
            lifecycleScope.launch {
                viewModel.createCustom().collect { result ->
                    result.onSuccess { customId ->
                        val bundle = Bundle()
                        bundle.putInt("customId", customId)
                        navController.navigate(
                            R.id.action_customerCartPageFragment_to_addDepartForNewCustomFragment,
                            bundle
                        )
                    }
                }
            }
        }
        buttonClearCart.setOnClickListener {
            viewModel.clearCart()
        }
    }

    private fun itemRemovedClick(): (Int) -> Unit {
        return { position ->
            viewModel.cartProductsArrayDTO.value.getOrNull(position)?.productId?.let { productId ->
                viewModel.removeProductFromCart(productId)
            }
        }
    }

}