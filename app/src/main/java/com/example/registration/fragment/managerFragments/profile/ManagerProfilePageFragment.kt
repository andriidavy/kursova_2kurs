package com.example.registration.fragment.managerFragments.profile

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
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.products.AddProductViewModel
import com.example.registration.viewmodel.manager.products.AddProductViewModelFactory
import com.example.registration.viewmodel.manager.profile.ManagerProfilePageViewModel
import com.example.registration.viewmodel.manager.profile.ManagerProfilePageViewModelFactory


class ManagerProfilePageFragment : Fragment() {
private lateinit var binding: FragmentManagerProfileBinding
private lateinit var viewModel: ManagerProfilePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerProfileBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            ManagerProfilePageViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[ManagerProfilePageViewModel::class.java]

        val navController = findNavController()

        val sharedManagerIdPreferences: SharedPreferences =
            requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedManagerIdPreferences)

        viewModel.getManagerProfileById().observe(viewLifecycleOwner) { manager ->
            binding.id.text = viewModel.managerId.toString()
            binding.name.text = manager?.name
            binding.surname.text = manager?.surname
            binding.email.text = manager?.email
        }

        binding.buttonLogout.setOnClickListener {
            navController.navigate(R.id.action_managerProfilePageFragment_to_loginFragment)
        }



        return binding.root
    }
}