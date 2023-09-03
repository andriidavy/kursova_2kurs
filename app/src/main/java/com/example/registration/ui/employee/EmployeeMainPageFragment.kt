package com.example.registration.ui.employee

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentEmployeeMainPageBinding

class EmployeeMainPageFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeMainPageBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeMainPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
    }

    private fun setViews() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        buttonToCustomInProgress.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeCustomsInProgressFragment)
        }

        buttonToCustomProcessed.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeCustomsProcessedFragment)
        }

        buttonToAcceptedReport.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeReportsAcceptedFragment)
        }

        buttonToWaitingReport.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeReportsInWaitingFragment)
        }

        buttonToRejectedReport.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeReportsRejectedFragment)
        }

        buttonToMyProfile.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeProfilePageFragment)
        }
    }
}