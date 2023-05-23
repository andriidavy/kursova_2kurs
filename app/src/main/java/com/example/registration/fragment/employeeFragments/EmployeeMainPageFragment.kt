package com.example.registration.fragment.employeeFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentEmployeeMainPageBinding


class EmployeeMainPageFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeMainPageBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeMainPageBinding.inflate(inflater)

        val navController = findNavController()

        binding.buttonToCustomInProgress.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeCustomsInProgressFragment)
        }

        binding.buttonToCustomProcessed.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeCustomsProcessedFragment)
        }

        binding.buttonToAcceptedReport.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeReportsAcceptedFragment)
        }

        binding.buttonToWaitingReport.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeReportsInWaitingFragment)
        }

        binding.buttonToRejectedReport.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeReportsRejectedFragment)
        }

        binding.buttonToMyProfile.setOnClickListener {
            navController.navigate(R.id.action_employeeMainPageFragment_to_employeeProfilePageFragment)
        }

        return binding.root
    }
}