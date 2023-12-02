package com.example.registration.ui.manager.profile

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
import com.example.registration.databinding.FragmentManagerProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManagerProfilePageFragment : Fragment() {

    private lateinit var binding: FragmentManagerProfileBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<ManagerProfilePageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
    }

    private fun setViews() = with(binding) {
        navController = findNavController()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.managerProfileDTO.collect { manager ->
                    manager.apply {
                        managerId.text = id.toString()
                        managerName.text = name
                        managerSurname.text = surname
                        managerEmail.text = email
                        managerDepartments.text = departmentDTOstring
                    }
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        buttonLogout.setOnClickListener {
            navController.navigate(R.id.action_managerProfilePageFragment_to_loginFragment)
        }
    }
}