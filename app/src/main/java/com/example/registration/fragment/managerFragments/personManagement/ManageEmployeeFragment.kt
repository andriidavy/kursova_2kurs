package com.example.registration.fragment.managerFragments.personManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.ManageEmployeeAdapter
import com.example.registration.databinding.FragmentManageEmployeeBinding
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.manager.ManagerApi
import com.example.registration.viewmodel.manager.personManagement.ManageEmployeeViewModel
import com.example.registration.viewmodel.manager.personManagement.ManageEmployeeViewModelFactory

class ManageEmployeeFragment : Fragment() {
    private lateinit var binding: FragmentManageEmployeeBinding
    private lateinit var viewModel: ManageEmployeeViewModel
    private lateinit var adapter: ManageEmployeeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManageEmployeeBinding.inflate(inflater)

        adapter = ManageEmployeeAdapter(emptyList())
        binding.manageEmployeesListRecyclerView.adapter = adapter
        binding.manageEmployeesListRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            ManageEmployeeViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[ManageEmployeeViewModel::class.java]

        val navController = findNavController()

        viewModel.employeeDTOArray.observe(viewLifecycleOwner) { employees ->
            adapter.updateEmployees(employees)
        }

        viewModel.getAllEmployeesProfile()


        fun removeEmployee(employeeId: Int) {
            viewModel.deleteEmployeeById(employeeId)
        }

        adapter.setOnRemoveEmployeeClickListener(object :
            ManageEmployeeAdapter.OnRemoveEmployeeClickListener {
            override fun onRemoveEmployeeClick(position: Int) {
                val employeeId : Int? = viewModel.employeeDTOArray.value?.get(position)?.id
                employeeId?.let { removeEmployee(it) }
            }
        })

        binding.buttonAddEmployee.setOnClickListener {
            navController.navigate(R.id.action_manageEmployeeFragment_to_addEmployeeFragment)
        }


        return binding.root
    }

}