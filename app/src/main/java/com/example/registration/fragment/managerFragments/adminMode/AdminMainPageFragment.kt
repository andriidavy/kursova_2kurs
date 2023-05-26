package com.example.registration.fragment.managerFragments.adminMode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentAdminLoginBinding
import com.example.registration.databinding.FragmentAdminMainPageBinding

class AdminMainPageFragment : Fragment() {
  private lateinit var binding: FragmentAdminMainPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminMainPageBinding.inflate(inflater)

        val navController = findNavController()

        binding.buttonToManagers.setOnClickListener {
            navController.navigate(R.id.action_adminMainPageFragment_to_editManagerFragment)
        }

        binding.buttonToDepartments.setOnClickListener {
            navController.navigate(R.id.action_adminMainPageFragment_to_editDepartsFragment)
        }


        return binding.root
    }
}