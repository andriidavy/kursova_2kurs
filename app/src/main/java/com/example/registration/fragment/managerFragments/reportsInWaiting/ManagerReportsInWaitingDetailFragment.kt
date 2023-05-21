package com.example.registration.fragment.managerFragments.reportsInWaiting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.registration.R
import com.example.registration.databinding.FragmentManagerReportsInWaitingDetailBinding
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.reportsInWaiting.ManagerReportsInWaitingDetailViewModel
import com.example.registration.viewmodel.manager.reportsInWaiting.ManagerReportsInWaitingDetailViewModelFactory
import com.example.registration.viewmodel.manager.reportsInWaiting.ManagerReportsInWaitingViewModel
import com.example.registration.viewmodel.manager.reportsInWaiting.ManagerReportsInWaitingViewModelFactory

class ManagerReportsInWaitingDetailFragment : Fragment() {
private lateinit var binding: FragmentManagerReportsInWaitingDetailBinding
private lateinit var viewModel: ManagerReportsInWaitingDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerReportsInWaitingDetailBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory = ManagerReportsInWaitingDetailViewModelFactory(managerRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[ManagerReportsInWaitingDetailViewModel::class.java]

        val reportId : Int? = arguments?.getInt("reportId")
        val reportText : String? = arguments?.getString("reportText")

        binding.reportText.text = reportText

        binding.buttonAcceptReport.setOnClickListener {
            if (reportId != null) {
                viewModel.setReportAccepted(reportId)
                binding.buttonAcceptReport.visibility = View.GONE
                binding.buttonRejectReport.visibility = View.GONE
                Toast.makeText(context,"Звіт прийнято!", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonRejectReport.setOnClickListener {
            if (reportId != null) {
                viewModel.setReportRejected(reportId)
                binding.buttonAcceptReport.visibility = View.GONE
                binding.buttonRejectReport.visibility = View.GONE
                Toast.makeText(context,"Звіт відхилено!", Toast.LENGTH_LONG).show()
            }
        }



        return binding.root
    }
}