package com.example.registration.fragment.customerFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.registration.databinding.FragmentRegistrationBinding
import com.example.registration.repository.CustomerRepository
import com.example.registration.retrofit.customerApi.CustomerApi
import com.example.registration.retrofit.RetrofitService
import com.example.registration.viewmodel.customer_registration.CustomerRegistrationViewModel
import com.example.registration.viewmodel.customer_registration.CustomerRegistrationViewModelFactory

class RegistrationFragment : Fragment() {
    private lateinit var viewModel: CustomerRegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentRegistrationBinding.inflate(inflater)
        val retrofitService = RetrofitService()
        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)
        val repository = CustomerRepository(customerApi)
        val viewModelFactory = CustomerRegistrationViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[CustomerRegistrationViewModel::class.java]
        binding.registrationViewModel = viewModel
        binding.lifecycleOwner = this

        // Получаем NavController
        val navController = findNavController()
        // Устанавливаем NavController в ViewModel
        viewModel.setNavController(navController)

        binding.buttonReg.setOnClickListener {
            viewModel.registrationCustomer()
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_SHORT
            ).show()
        }

        return binding.root
    }

}