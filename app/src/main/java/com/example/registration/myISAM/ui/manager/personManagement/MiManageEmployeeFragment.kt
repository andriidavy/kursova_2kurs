package com.example.registration.myISAM.ui.manager.personManagement

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
import com.example.registration.adapter.custom.ManageEmployeeAdapter
import com.example.registration.databinding.FragmentManageEmployeeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiManageEmployeeFragment : Fragment() {

    private lateinit var binding: FragmentManageEmployeeBinding
    private lateinit var adapter: ManageEmployeeAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<MiManageEmployeeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManageEmployeeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
        setListeners()
    }

    private fun setViews() = with(binding) {
        adapter = ManageEmployeeAdapter(emptyList(), onRemoveEmployeeClick())
        manageEmployeesListRecyclerView.adapter = adapter
        manageEmployeesListRecyclerView.layoutManager = LinearLayoutManager(activity)

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

    private fun setListeners() = with(binding) {
        buttonAddEmployee.setOnClickListener {
            navController.navigate(R.id.action_miManageEmployeeFragment_to_miAddEmployeeFragment)
        }
    }

    private fun onRemoveEmployeeClick(): (Int) -> Unit {
        return { position ->
            val employeeId: Int = viewModel.employeeDTOArray.value[position].id
            viewModel.deleteEmployeeById(employeeId)
        }
    }
}