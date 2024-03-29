package com.example.registration.fragment.managerFragments.adminMode.departments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.manager.ManageManagerAdapter
import com.example.registration.adapter.manager.department.ManagerDepartmentAdapter
import com.example.registration.databinding.FragmentEditDepartsBinding
import com.example.registration.model.department.DepartmentDTO
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.adminMode.EditManagerViewModel
import com.example.registration.viewmodel.manager.adminMode.EditManagerViewModelFactory
import com.example.registration.viewmodel.manager.adminMode.editDeparts.EditDepartsViewModel
import com.example.registration.viewmodel.manager.adminMode.editDeparts.EditDepartsViewModelFactory


class EditDepartsFragment : Fragment() {
    private lateinit var binding: FragmentEditDepartsBinding
    private lateinit var viewModel : EditDepartsViewModel
    private lateinit var adapter: ManagerDepartmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditDepartsBinding.inflate(inflater)

        adapter = ManagerDepartmentAdapter(emptyList())
        binding.editDepartsRecyclerView.adapter = adapter
        binding.editDepartsRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            EditDepartsViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[EditDepartsViewModel::class.java]

        val navController = findNavController()

        viewModel.getAllDepartments()

        viewModel.departAllArray.observe(viewLifecycleOwner) { departs ->
            adapter.updateDepartments(departs)
        }

        fun removeDepartment(departmentId: Int) {
            viewModel.removeDepartmentById(departmentId)
        }

        adapter.setOnRemoveManagerClickListener(object :
            ManagerDepartmentAdapter.OnRemoveManagerClickListener {
            override fun onRemoveManagerClick(position: Int) {
                val departmentId : Int? = viewModel.departAllArray.value?.get(position)?.id
                departmentId?.let { removeDepartment(it) }
            }
        })

        binding.buttonToAddDeparts.setOnClickListener {
            navController.navigate(R.id.action_editDepartsFragment_to_addDepartFragment)
        }

        return binding.root
    }

}