package com.example.registration.fragment.employeeFragments.reportsRejected

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.report.ReportAdapter
import com.example.registration.databinding.FragmentEmployeeReportsInWaitingBinding
import com.example.registration.databinding.FragmentEmployeeReportsRejectedBinding
import com.example.registration.repository.EmployeeRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.employeeApi.EmployeeApi
import com.example.registration.viewmodel.employee.reports.inWaiting.EmployeeReportsInWaitingViewModel
import com.example.registration.viewmodel.employee.reports.inWaiting.EmployeeReportsInWaitingViewModelFactory
import com.example.registration.viewmodel.employee.reports.rejected.EmployeeReportsRejectedViewModel
import com.example.registration.viewmodel.employee.reports.rejected.EmployeeReportsRejectedViewModelFactory


class EmployeeReportsRejectedFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeReportsRejectedBinding
    private lateinit var viewModel : EmployeeReportsRejectedViewModel
    private lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeReportsRejectedBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val employeeApi = retrofitService.retrofit.create(EmployeeApi::class.java)
        val employeeRepository = EmployeeRepository(employeeApi)
        val viewModelFactory =
            EmployeeReportsRejectedViewModelFactory(employeeRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[EmployeeReportsRejectedViewModel::class.java]

        val sharedEmployeeIdPreferences: SharedPreferences = requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedEmployeeIdPreferences)

        adapter = ReportAdapter(emptyList())
        binding.employeeReportsRejectedRecyclerView.adapter = adapter
        binding.employeeReportsRejectedRecyclerView.layoutManager = LinearLayoutManager(activity)

        val navController = findNavController()

        viewModel.reportRejectedArray.observe(viewLifecycleOwner) { reports ->
            adapter.updateReports(reports)
        }

        viewModel.getRejectedReportsForEmployee()

        adapter.setOnItemClickListener(object : ReportAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //test
                Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                val reportText: String? = viewModel.reportRejectedArray.value?.get(position)?.reportText
                bundle.putString("reportText", reportText)
                navController.navigate(R.id.action_employeeReportsRejectedFragment_to_reportDetailFragment ,bundle)
            }
        })

        return binding.root
    }
}