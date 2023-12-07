package com.example.registration.myISAM.ui.manager.adminMode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentAdminMainPageBinding

class MiAdminMainPageFragment : Fragment() {

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
            navController.navigate(R.id.action_miAdminMainPageFragment_to_miEditManagerFragment)
        }

        buttonToDepartments.setOnClickListener {
            navController.navigate(R.id.action_miAdminMainPageFragment_to_miEditDepartsFragment)
        }

        buttonToStaff.setOnClickListener {
            navController.navigate(R.id.action_miAdminMainPageFragment_to_miStaffFragment)
        }
    }
}