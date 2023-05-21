package com.example.registration.fragment.managerFragments.reportsInWaiting

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
import com.example.registration.databinding.FragmentManagerReportsInWaitingBinding
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.createdCustoms.ManagerCreatedCustomsPageViewModel
import com.example.registration.viewmodel.manager.createdCustoms.ManagerCreatedCustomsPageViewModelFactory
import com.example.registration.viewmodel.manager.reportsInWaiting.ManagerReportsInWaitingViewModel
import com.example.registration.viewmodel.manager.reportsInWaiting.ManagerReportsInWaitingViewModelFactory


class ManagerReportsInWaitingFragment : Fragment() {
    private lateinit var binding: FragmentManagerReportsInWaitingBinding
    private lateinit var viewModel: ManagerReportsInWaitingViewModel
    private lateinit var adapter: ReportAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerReportsInWaitingBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory = ManagerReportsInWaitingViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[ManagerReportsInWaitingViewModel::class.java]

        adapter = ReportAdapter(emptyList())
        binding.managerReportsInWaitingRecyclerView.adapter = adapter
        binding.managerReportsInWaitingRecyclerView.layoutManager = LinearLayoutManager(activity)

        val navController = findNavController()

        viewModel.reportInWaitingArray.observe(viewLifecycleOwner) { reports ->
            adapter.updateReports(reports)
        }

        viewModel.getAllInWaitingReports()

        adapter.setOnItemClickListener(object : ReportAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //test
                Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                val reportText: String? = viewModel.reportInWaitingArray.value?.get(position)?.reportText
                val reportId: Int? = viewModel.reportInWaitingArray.value?.get(position)?.reportId
                    bundle.putString("reportText", reportText)
                if (reportId != null) {
                    bundle.putInt("reportId", reportId)
                }
                navController.navigate(
                    R.id.action_managerReportsInWaitingFragment_to_managerReportsInWaitingDetailFragment,
                    bundle
                )
            }
        })

        return binding.root
    }
}