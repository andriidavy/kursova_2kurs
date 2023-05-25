package com.example.registration.fragment.managerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentManagerMainPageBinding

class ManagerMainPageFragment : Fragment() {
private lateinit var binding: FragmentManagerMainPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerMainPageBinding.inflate(inflater)

        val navController = findNavController()

        binding.buttonToCreatedCustoms.setOnClickListener {
            navController.navigate(R.id.action_managerMainPageFragment_to_managerCreatedCustomsPageFragment)
        }

        binding.buttonToWaitingReport.setOnClickListener {
            navController.navigate(R.id.action_managerMainPageFragment_to_managerReportsInWaitingFragment)
        }

        binding.buttonToAllCustoms.setOnClickListener {
            navController.navigate(R.id.action_managerMainPageFragment_to_managerAllCustomsFragment)
        }

        binding.buttonToProductList.setOnClickListener {
            navController.navigate(R.id.action_managerMainPageFragment_to_allProductListFragment)
        }

        binding.buttonToHr.setOnClickListener {
            navController.navigate(R.id.action_managerMainPageFragment_to_manageEmployeeFragment)
        }

        binding.buttonToMyProfile.setOnClickListener {
            navController.navigate(R.id.action_managerMainPageFragment_to_managerProfilePageFragment)
        }

        binding.buttonToAdminMode.setOnClickListener {
            navController.navigate(R.id.action_managerMainPageFragment_to_adminLoginFragment)
        }

        return binding.root
    }

}