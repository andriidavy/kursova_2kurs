package com.example.registration.fragment.customerFragments.profile

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
import com.example.registration.databinding.FragmentCustomerProfileBinding
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.customer.CustomerApi
import com.example.registration.viewmodel.customer.profile.CustomerProfilePageViewModel
import com.example.registration.viewmodel.customer.profile.CustomerProfilePageViewModelFactory


class CustomerProfilePageFragment : Fragment() {
private lateinit var binding: FragmentCustomerProfileBinding
private lateinit var viewModel: CustomerProfilePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerProfileBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)
        val customerRepository = CustomerRepository(customerApi)
        val viewModelFactory =
            CustomerProfilePageViewModelFactory(customerRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CustomerProfilePageViewModel::class.java]

        val navController = findNavController()

        val sharedCustomerIdPreferences: SharedPreferences =
            requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedCustomerIdPreferences)

        viewModel.getCustomerProfileById().observe(viewLifecycleOwner) { customer ->
            // Обновление значений полей после получения данных профиля
            binding.customerId.text = viewModel.customerId.toString()
            binding.customerName.text = customer?.name
            binding.customerSurname.text = customer?.surname
            binding.customerEmail.text = customer?.email
        }

        binding.buttonLogout.setOnClickListener {
            navController.navigate(R.id.action_customerProfilePageFragment_to_loginFragment)
        }

        return binding.root
    }

}