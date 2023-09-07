package com.example.registration.ui.employee.customsInProgress

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
import com.example.registration.adapter.custom.EmployeeCustomInProgressAdapter
import com.example.registration.databinding.FragmentEmployeeCustomInProgressBinding
import com.example.registration.model.custom.CustomProductDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeeCustomsInProgressFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeCustomInProgressBinding
    private lateinit var adapter: EmployeeCustomInProgressAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<EmployeeCustomsInProgressViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeCustomInProgressBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() = with(binding) {
        adapter = EmployeeCustomInProgressAdapter(emptyList(), onItemClick(), onCreateReportClick())
        employeeCustomInProgressRecyclerView.adapter = adapter
        employeeCustomInProgressRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customInProgressArray.collect { customs ->
                    adapter.updateCustoms(customs)
                }
            }
        }
    }

    private fun onCreateReportClick(): (Int) -> Unit {
        return { customId ->
            val bundle = Bundle()
            bundle.putInt("customInProgressId", customId)
            navController.navigate(
                R.id.action_employeeCustomsInProgressFragment_to_creatingReportForCustomFragment,
                bundle
            )
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val list: ArrayList<CustomProductDTO>? =
                viewModel.customInProgressArray.value[position].customProductList as ArrayList<CustomProductDTO>?
            bundle.putParcelableArrayList("customInProgressProductList", list)
            navController.navigate(
                R.id.action_employeeCustomsInProgressFragment_to_customInProgressProductDetailFragment,
                bundle
            )
        }
    }
}