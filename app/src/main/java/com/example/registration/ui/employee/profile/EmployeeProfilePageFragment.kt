package com.example.registration.ui.employee.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentEmployeeProfilePageBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeeProfilePageFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeProfilePageBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<EmployeeProfilePageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeProfilePageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
        setObservers()
    }

    private fun setViews() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        buttonLogout.setOnClickListener {
            navController.navigate(R.id.action_employeeProfilePageFragment_to_loginFragment)
        }
    }

    private fun setObservers() = with(binding) {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.employeeProfileDTO.collect { employee ->
                    employee.apply {
                        employeeId.text = id.toString()
                        employeeName.text = name
                        employeeSurname.text = surname
                        employeeEmail.text = email
                    }
                }
            }
        }
    }
}