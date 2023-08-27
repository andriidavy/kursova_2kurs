package com.example.registration.ui.manager.createdCustoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.EmployeeForAssigneeAdapter
import com.example.registration.databinding.FragmentEmployeesListForAssigneeToCustomBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeesListForAssigneeToCustomFragment : Fragment() {

    private lateinit var binding: FragmentEmployeesListForAssigneeToCustomBinding
    private lateinit var adapter: EmployeeForAssigneeAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<EmployeesListForAssigneeToCustomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeesListForAssigneeToCustomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() = with(binding) {
        adapter = EmployeeForAssigneeAdapter(emptyList(), onItemClick())
        employeesListForAssigneeToCustomRecyclerView.adapter = adapter
        employeesListForAssigneeToCustomRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.employeeDTOArray.collect { employees ->
                    adapter.updateEmployees(employees)
                }
            }
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val customId: Int? = arguments?.getInt("customId")
            val employeeId: Int = viewModel.employeeDTOArray.value[position].id

            customId?.let {
                viewModel.assignEmployeeToCustom(customId, employeeId)

                ToastObj.shortToastMake(
                    getString(
                        R.string.assign_custom_to_employee,
                        customId.toString(),
                        employeeId.toString()
                    ),
                    context
                )

                navController.navigate(R.id.action_employeesListForAssigneeToCustomFragment_to_managerCreatedCustomsPageFragment)
            } ?: ToastObj.shortToastMake(getString(R.string.error_custom_id), context)
        }
    }
}