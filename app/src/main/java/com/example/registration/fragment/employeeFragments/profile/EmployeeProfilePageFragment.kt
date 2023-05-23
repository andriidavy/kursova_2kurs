package com.example.registration.fragment.employeeFragments.profile

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
import com.example.registration.databinding.FragmentEmployeeProfilePageBinding
import com.example.registration.databinding.FragmentManagerProfileBinding
import com.example.registration.repository.EmployeeRepository
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.employeeApi.EmployeeApi
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.employee.profile.EmployeeProfilePageViewModel
import com.example.registration.viewmodel.employee.profile.EmployeeProfilePageViewModelFactory
import com.example.registration.viewmodel.manager.profile.ManagerProfilePageViewModel
import com.example.registration.viewmodel.manager.profile.ManagerProfilePageViewModelFactory

class EmployeeProfilePageFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeProfilePageBinding
    private lateinit var viewModel: EmployeeProfilePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeProfilePageBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val employeeApi = retrofitService.retrofit.create(EmployeeApi::class.java)
        val employeeRepository = EmployeeRepository(employeeApi)
        val viewModelFactory =
            EmployeeProfilePageViewModelFactory(employeeRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[EmployeeProfilePageViewModel::class.java]

        val navController = findNavController()

        val sharedManagerIdPreferences: SharedPreferences =
            requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedManagerIdPreferences)

        viewModel.getEmployeeProfileById().observe(viewLifecycleOwner) { employee ->
            binding.id.text = viewModel.employeeId.toString()
            binding.name.text = employee?.name
            binding.surname.text = employee?.surname
            binding.email.text = employee?.email
        }

        binding.buttonLogout.setOnClickListener {
            navController.navigate(R.id.action_employeeProfilePageFragment_to_loginFragment)
        }



        return binding.root
    }
}