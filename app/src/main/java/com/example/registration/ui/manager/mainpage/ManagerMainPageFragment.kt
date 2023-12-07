package com.example.registration.ui.manager.mainpage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentManagerMainPageBinding

class ManagerMainPageFragment : Fragment() {

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
                navigate(R.id.action_managerMainPageFragment_to_managerCreatedCustomsPageFragment)
            }

            buttonToWaitingReport.setOnClickListener {
                navigate(R.id.action_managerMainPageFragment_to_managerReportsInWaitingFragment)
            }

            buttonToAllCustoms.setOnClickListener {
                navigate(R.id.action_managerMainPageFragment_to_managerAllCustomsFragment)
            }

            buttonToProductList.setOnClickListener {
                navigate(R.id.action_managerMainPageFragment_to_allProductListFragment)
            }

            buttonToHr.setOnClickListener {
                navigate(R.id.action_managerMainPageFragment_to_manageEmployeeFragment)
            }

            buttonToMyProfile.setOnClickListener {
                navigate(R.id.action_managerMainPageFragment_to_managerProfilePageFragment)
            }

            buttonToAdminMode.setOnClickListener {
                navigate(R.id.action_managerMainPageFragment_to_adminLoginFragment)
            }
        }
    }
}