package com.example.registration.fragment.employeeFragments.reportsAccepted

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
import com.example.registration.databinding.FragmentEmployeeReportsAcceptedBinding
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.employee.EmployeeApi
import com.example.registration.viewmodel.employee.reports.accepted.EmployeeReportsAcceptedViewModel
import com.example.registration.viewmodel.employee.reports.accepted.EmployeeReportsAcceptedViewModelFactory

class EmployeeReportsAcceptedFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeReportsAcceptedBinding
    private lateinit var viewModel : EmployeeReportsAcceptedViewModel
    private lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmployeeReportsAcceptedBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val employeeApi = retrofitService.retrofit.create(EmployeeApi::class.java)
        val employeeRepository = EmployeeRepository(employeeApi)
        val viewModelFactory =
            EmployeeReportsAcceptedViewModelFactory(employeeRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[EmployeeReportsAcceptedViewModel::class.java]

        val sharedEmployeeIdPreferences: SharedPreferences = requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedEmployeeIdPreferences)

        adapter = ReportAdapter(emptyList())
        binding.employeeReportsAcceptedRecyclerView.adapter = adapter
        binding.employeeReportsAcceptedRecyclerView.layoutManager = LinearLayoutManager(activity)

        val navController = findNavController()

        viewModel.reportAcceptedArray.observe(viewLifecycleOwner) { reports ->
            adapter.updateReports(reports)
        }

        viewModel.getAcceptedReportsForEmployee()

        adapter.setOnItemClickListener(object : ReportAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //test
                Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                val reportText: String? = viewModel.reportAcceptedArray.value?.get(position)?.reportText
                bundle.putString("reportText", reportText)
                navController.navigate(R.id.action_employeeReportsAcceptedFragment_to_reportDetailFragment, bundle)
            }
        })

        return binding.root
    }


}