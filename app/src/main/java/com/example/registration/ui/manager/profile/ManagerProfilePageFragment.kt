package com.example.registration.ui.manager.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentManagerProfileBinding
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.manager.ManagerApi


class ManagerProfilePageFragment : Fragment() {
private lateinit var binding: FragmentManagerProfileBinding
private lateinit var viewModel: ManagerProfilePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerProfileBinding.inflate(inflater)

        val navController = findNavController()

        val sharedManagerIdPreferences: SharedPreferences =
            requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedManagerIdPreferences)

        viewModel.getManagerProfileById().observe(viewLifecycleOwner) { manager ->
            binding.id.text = viewModel.managerId.toString()
            binding.name.text = manager?.name
            binding.surname.text = manager?.surname
            binding.email.text = manager?.email
            binding.departments.text = manager?.departmentDTOstring
        }

        binding.buttonLogout.setOnClickListener {
            navController.navigate(R.id.action_managerProfilePageFragment_to_loginFragment)
        }

        return binding.root
    }
}