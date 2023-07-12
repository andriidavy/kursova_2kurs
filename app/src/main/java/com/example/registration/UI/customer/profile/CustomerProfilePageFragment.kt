package com.example.registration.UI.customer.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentCustomerProfileBinding
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerProfilePageFragment : Fragment() {
private lateinit var binding: FragmentCustomerProfileBinding

    private val viewModel by viewModels<CustomerProfilePageViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerProfileBinding.inflate(inflater)

        val navController = findNavController()

        val customerId = dataStoreViewModel.getUserId()

        viewModel.getCustomerProfileById(customerId).observe(viewLifecycleOwner) { customer ->
            // Обновление значений полей после получения данных профиля
            binding.customerId.text = customerId.toString()
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