package com.example.registration.myISAM.ui.customer.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentCustomerMainPageBinding

class MiCustomerMainPageFragment : Fragment() {
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
                navigate(R.id.action_miCustomerMainPageFragment_to_miCustomerCartPageFragment)
            }

            buttonToCustoms.setOnClickListener {
                navigate(R.id.action_miCustomerMainPageFragment_to_miCustomerCustomPageFragment)
            }

            buttonToProfile.setOnClickListener {
                navigate(R.id.action_miCustomerMainPageFragment_to_miCustomerProfilePageFragment)
            }
            buttonToProductList.setOnClickListener {
                navigate(R.id.action_miCustomerMainPageFragment_to_miCustomerProductsListFragment)
            }
        }
    }
}