package com.example.registration.myISAM.ui.employee.reportsRejected

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.report.ReportAdapter
import com.example.registration.databinding.FragmentEmployeeReportsRejectedBinding
import com.example.registration.ui.employee.reportsRejected.EmployeeReportsRejectedViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiEmployeeReportsRejectedFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeReportsRejectedBinding
    private lateinit var adapter: ReportAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<MiEmployeeReportsRejectedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeReportsRejectedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() = with(binding){
        adapter = ReportAdapter(emptyList(), onItemClick())
        employeeReportsRejectedRecyclerView.adapter = adapter
        employeeReportsRejectedRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.reportRejectedArray.collect { reports ->
                    adapter.updateReports(reports)
                }
            }
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val reportText: String? =
                viewModel.reportRejectedArray.value[position].reportText
            bundle.putString("reportText", reportText)
            navController.navigate(
                R.id.action_miEmployeeReportsRejectedFragment_to_miReportDetailFragment,
                bundle
            )
        }
    }
}