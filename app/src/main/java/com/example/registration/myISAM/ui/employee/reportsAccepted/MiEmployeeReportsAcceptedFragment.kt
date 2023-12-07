package com.example.registration.myISAM.ui.employee.reportsAccepted

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.report.ReportAdapter
import com.example.registration.databinding.FragmentEmployeeReportsAcceptedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MiEmployeeReportsAcceptedFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeReportsAcceptedBinding
    private lateinit var adapter: ReportAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<MiEmployeeReportsAcceptedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeReportsAcceptedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() = with(binding) {
        adapter = ReportAdapter(emptyList(), onItemClick())
        employeeReportsAcceptedRecyclerView.adapter = adapter
        employeeReportsAcceptedRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        viewModel.reportAcceptedArray.observe(viewLifecycleOwner) { reports ->
            adapter.updateReports(reports)
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val reportText: String? = viewModel.reportAcceptedArray.value?.get(position)?.reportText
            bundle.putString("reportText", reportText)
            navController.navigate(
                R.id.action_miEmployeeReportsAcceptedFragment_to_miReportDetailFragment,
                bundle
            )
        }
    }
}