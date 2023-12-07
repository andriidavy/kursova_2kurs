package com.example.registration.myISAM.ui.customer.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.MainActivity
import com.example.registration.databinding.FragmentCustomerProfileBinding
import com.example.registration.ui.customer.profile.CustomerProfilePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiCustomerProfilePageFragment : Fragment() {
    private lateinit var binding: FragmentCustomerProfileBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<MiCustomerProfilePageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }

    private fun setObservers() = with(binding) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customer.collect { customer ->
                    customer.apply {
                        customerId.text = id.toString()
                        customerName.text = name
                        customerSurname.text = surname
                        customerEmail.text = email
                    }
                }
            }
        }
    }
}