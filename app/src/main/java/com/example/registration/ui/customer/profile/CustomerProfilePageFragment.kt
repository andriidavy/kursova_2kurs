package com.example.registration.ui.customer.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentCustomerProfileBinding
import com.example.registration.datastore.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerProfilePageFragment : Fragment() {
    private lateinit var binding: FragmentCustomerProfileBinding
    private lateinit var navController: NavController

    private val viewModel by viewModels<CustomerProfilePageViewModel>()
    private val dataStoreViewModel by viewModels<DataStoreViewModel>()

    private val userId = dataStoreViewModel.getUserId()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCustomerProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupView()
        setListeners()
        setObservers()
    }

    private fun setupView() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        buttonLogout.setOnClickListener {
            navController.navigate(R.id.action_customerProfilePageFragment_to_loginFragment)
        }
    }

    private fun setObservers() = with(binding) {
        viewModel.getCustomerProfileById(userId).observe(viewLifecycleOwner) { customer ->
            // Обновление значений полей после получения данных профиля
            customerId.text = customerId.toString()
            customerName.text = customer?.name
            customerSurname.text = customer?.surname
            customerEmail.text = customer?.email
        }
    }


}