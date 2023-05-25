package com.example.registration.fragment.managerFragments.adminMode.managers

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
import com.example.registration.databinding.FragmentManagerDepartDetailBinding
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.adminMode.EditManagerViewModel
import com.example.registration.viewmodel.manager.adminMode.EditManagerViewModelFactory
import com.example.registration.viewmodel.manager.adminMode.ManagerDepartDetailViewModel
import com.example.registration.viewmodel.manager.adminMode.ManagerDepartDetailViewModelFactory

class ManagerDepartDetailFragment : Fragment() {
    private lateinit var binding: FragmentManagerDepartDetailBinding
    private lateinit var viewModel: ManagerDepartDetailViewModel
    private lateinit var adapter: ManagerDepartmentAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerDepartDetailBinding.inflate(inflater)

        adapter = ManagerDepartmentAdapter(emptyList())
        binding.managerDepartDetailRecyclerView.adapter = adapter
        binding.managerDepartDetailRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            ManagerDepartDetailViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[ManagerDepartDetailViewModel::class.java]

        val navController = findNavController()

        val managerId: Int? = arguments?.getInt("managerId")

        managerId?.let { viewModel.getAllDepartmentsForManager(it) }


        binding.buttonAddDepart.setOnClickListener {
            val bundle = Bundle()
            managerId?.let { it1 -> bundle.putInt("managerId", it1) }
            navController.navigate(R.id.action_managerDepartDetailFragment_to_allDepartFragment, bundle)
        }

        viewModel.departForManagerArray.observe(viewLifecycleOwner) { departs ->
            adapter.updateDepartments(departs)
        }

        fun removeDepartForManager(managerId: Int, departmentId: Int) {
            viewModel.removeDepartmentFromManager(managerId, departmentId)
        }

        adapter.setOnRemoveManagerClickListener(object :
            ManagerDepartmentAdapter.OnRemoveManagerClickListener {
            override fun onRemoveManagerClick(position: Int) {
                val departmentId: Int? = viewModel.departForManagerArray.value?.get(position)?.id
                if (departmentId != null && managerId != null) {
                    removeDepartForManager(managerId, departmentId)
                }
            }
        })

        return binding.root
    }

}