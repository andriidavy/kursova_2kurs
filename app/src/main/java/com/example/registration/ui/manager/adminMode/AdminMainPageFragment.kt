package com.example.registration.ui.manager.adminMode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentAdminMainPageBinding

class AdminMainPageFragment : Fragment() {

    private lateinit var binding: FragmentAdminMainPageBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminMainPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
    }

    private fun setViews() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        buttonToManagers.setOnClickListener {
            navController.navigate(R.id.action_adminMainPageFragment_to_editManagerFragment)
        }

        buttonToDepartments.setOnClickListener {
            navController.navigate(R.id.action_adminMainPageFragment_to_editDepartsFragment)
        }
    }
}