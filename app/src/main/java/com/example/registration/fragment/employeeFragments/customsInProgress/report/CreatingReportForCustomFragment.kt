package com.example.registration.fragment.employeeFragments.customsInProgress.report

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.registration.R
import com.example.registration.databinding.FragmentCreatingReportForCustomBinding
import com.example.registration.repository.EmployeeRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.employeeApi.EmployeeApi
import com.example.registration.viewmodel.employee.customsInProgress.EmployeeCustomsInProgressViewModel
import com.example.registration.viewmodel.employee.customsInProgress.EmployeeCustomsInProgressViewModelFactory
import com.example.registration.viewmodel.employee.customsInProgress.report.CreatingReportForCustomViewModel
import com.example.registration.viewmodel.employee.customsInProgress.report.CreatingReportForCustomViewModelFactory


class CreatingReportForCustomFragment : Fragment() {
    private lateinit var binding: FragmentCreatingReportForCustomBinding
    private lateinit var viewModel: CreatingReportForCustomViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatingReportForCustomBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val employeeApi = retrofitService.retrofit.create(EmployeeApi::class.java)
        val employeeRepository = EmployeeRepository(employeeApi)
        val viewModelFactory =
            CreatingReportForCustomViewModelFactory(employeeRepository)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[CreatingReportForCustomViewModel::class.java]

        val sharedEmployeeIdPreferences: SharedPreferences =
            requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedEmployeeIdPreferences)

        val customId: Int? = arguments?.getInt("customInProgressId")

        binding.reportId.text = customId.toString()

        binding.buttonSendReport.setOnClickListener {
            if (customId != null) {
                val reportText: String = binding.reportText.text.toString()
                viewModel.createReport(customId, reportText)
                Toast.makeText(
                    context,
                    "створено звіт для замовлення № $customId",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        return binding.root
    }

}