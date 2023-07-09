package com.example.registration.UI.customer.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.registration.databinding.FragmentRegistrationBinding
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.customer.CustomerApi
import com.example.registration.database.RetrofitService

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: CustomerRegistrationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)
        val repository = CustomerRepository(customerApi)
        val viewModelFactory = CustomerRegistrationViewModelFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[CustomerRegistrationViewModel::class.java]

        val navController = findNavController()
        viewModel.setNavController(navController)

        binding.buttonReg.setOnClickListener {
            val name: String = binding.etName.text.toString()
            val surname: String = binding.etSurname.text.toString()
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            if (name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.insertCustomer(name, surname, email, password)
                binding.etName.text.clear()
                binding.etSurname.text.clear()
                binding.etEmail.text.clear()
                binding.etPassword.text.clear()
            } else {
                viewModel.message.value = "Всі поля мають бути заповнені!"
            }
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