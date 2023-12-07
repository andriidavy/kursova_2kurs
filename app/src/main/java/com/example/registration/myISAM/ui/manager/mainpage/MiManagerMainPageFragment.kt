package com.example.registration.myISAM.ui.manager.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentManagerMainPageBinding

class MiManagerMainPageFragment : Fragment() {
    private lateinit var binding: FragmentManagerMainPageBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerMainPageBinding.inflate(inflater)
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
            buttonToCreatedCustoms.setOnClickListener {
                navigate(R.id.action_miManagerMainPageFragment_to_miManagerCreatedCustomsPageFragment)
            }

            buttonToWaitingReport.setOnClickListener {
               navigate(R.id.action_miManagerMainPageFragment_to_miManagerReportsInWaitingFragment)
            }

            buttonToAllCustoms.setOnClickListener {
                navigate(R.id.action_miManagerMainPageFragment_to_miManagerAllCustomsFragment)
            }

            buttonToProductList.setOnClickListener {
                navigate(R.id.action_miManagerMainPageFragment_to_miAllProductListFragment)
            }

            buttonToHr.setOnClickListener {
                navigate(R.id.action_miManagerMainPageFragment_to_miManageEmployeeFragment)
            }

            buttonToMyProfile.setOnClickListener {
                navigate(R.id.action_miManagerMainPageFragment_to_miManagerProfilePageFragment)
            }

            buttonToAdminMode.setOnClickListener {
                navigate(R.id.action_miManagerMainPageFragment_to_miAdminLoginFragment)
            }
        }
    }
}