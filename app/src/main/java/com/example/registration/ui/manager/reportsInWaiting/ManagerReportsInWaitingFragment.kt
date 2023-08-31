package com.example.registration.ui.manager.reportsInWaiting

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
import com.example.registration.databinding.FragmentManagerReportsInWaitingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManagerReportsInWaitingFragment : Fragment() {

    private lateinit var binding: FragmentManagerReportsInWaitingBinding
    private lateinit var adapter: ReportAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<ManagerReportsInWaitingViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerReportsInWaitingBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() = with(binding) {
        adapter = ReportAdapter(emptyList(), onItemClick())
        managerReportsInWaitingRecyclerView.adapter = adapter
        managerReportsInWaitingRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.reportInWaitingArray.collect { reports ->
                    adapter.updateReports(reports)
                }
            }
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val reportText: String? = viewModel.reportInWaitingArray.value[position].reportText
            val reportId: Int = viewModel.reportInWaitingArray.value[position].reportId

            bundle.putString("reportText", reportText)
            bundle.putInt("reportId", reportId)

            navController.navigate(
                R.id.action_managerReportsInWaitingFragment_to_managerReportsInWaitingDetailFragment,
                bundle
            )
        }
    }
}