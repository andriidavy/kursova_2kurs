package com.example.registration.fragment.managerFragments.createdCustoms

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
import com.example.registration.adapter.custom.CustomProductAdapter
import com.example.registration.adapter.custom.EmployeeForAssigneeAdapter
import com.example.registration.adapter.custom.ManagerCreatedCustomAdapter
import com.example.registration.databinding.FragmentEmployeesListForAssigneeToCustomBinding
import com.example.registration.model.custom.CustomProductDTO
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.createdCustoms.EmployeesListForAssigneeToCustomViewModel
import com.example.registration.viewmodel.manager.createdCustoms.EmployeesListForAssigneeToCustomViewModelFactory
import com.example.registration.viewmodel.manager.createdCustoms.ManagerCreatedCustomsPageViewModel
import com.example.registration.viewmodel.manager.createdCustoms.ManagerCreatedCustomsPageViewModelFactory


class EmployeesListForAssigneeToCustomFragment : Fragment() {
    private lateinit var binding: FragmentEmployeesListForAssigneeToCustomBinding
    private lateinit var viewModel: EmployeesListForAssigneeToCustomViewModel
    private lateinit var adapter: EmployeeForAssigneeAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmployeesListForAssigneeToCustomBinding.inflate(inflater)

        adapter = EmployeeForAssigneeAdapter(emptyList())
        binding.employeesListForAssigneeToCustomRecyclerView.adapter = adapter
        binding.employeesListForAssigneeToCustomRecyclerView.layoutManager =
            LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            EmployeesListForAssigneeToCustomViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[EmployeesListForAssigneeToCustomViewModel::class.java]

        val navController = findNavController()

        val customId: Int? = arguments?.getInt("customId")

        viewModel.getAllEmployeesProfile()

        viewModel.employeeDTOArray.observe(viewLifecycleOwner) { employees ->
            adapter.updateEmployees(employees)
        }

        adapter.setOnItemClickListener(object : EmployeeForAssigneeAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val employeeId : Int? = viewModel.employeeDTOArray.value?.get(position)?.id
                if (customId != null && employeeId != null) {
                    viewModel.assignEmployeeToCustom(customId, employeeId)
                }
                Toast.makeText(
                    activity,
                    "Для замовлення № $customId призначений робітник № $employeeId",
                    Toast.LENGTH_SHORT
                ).show()
                navController.navigate(R.id.action_employeesListForAssigneeToCustomFragment_to_managerCreatedCustomsPageFragment)
            }
        })



        return binding.root
    }
}