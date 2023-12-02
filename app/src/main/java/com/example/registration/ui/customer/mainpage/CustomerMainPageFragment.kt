package com.example.registration.ui.customer.mainpage

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
import com.example.registration.databinding.FragmentCustomerMainPageBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

class CustomerMainPageFragment : Fragment() {

    private lateinit var binding: FragmentCustomerMainPageBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCustomerMainPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        navController.apply {
            buttonToCart.setOnClickListener {
                navigate(R.id.action_customerMainPageFragment_to_customerCartPageFragment)
            }

            buttonToCustoms.setOnClickListener {
                navigate(R.id.action_customerMainPageFragment_to_customerCustomPageFragment)
            }

            buttonToProfile.setOnClickListener {
                navigate(R.id.action_customerMainPageFragment_to_customerProfilePageFragment)
            }
            buttonToProductList.setOnClickListener {
                navigate(R.id.action_customerMainPageFragment_to_customerProductsListFragment)
            }
        }
    }
}