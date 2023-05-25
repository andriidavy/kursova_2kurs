package com.example.registration.fragment.managerFragments.adminMode.managers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.manager.ManageManagerAdapter
import com.example.registration.adapter.manager.department.AllDepartmentAdapter
import com.example.registration.databinding.FragmentAllDepartBinding
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.adminMode.AllDepartViewModel
import com.example.registration.viewmodel.manager.adminMode.AllDepartViewModelFactory
import com.example.registration.viewmodel.manager.adminMode.EditManagerViewModel
import com.example.registration.viewmodel.manager.adminMode.EditManagerViewModelFactory


class AllDepartFragment : Fragment() {
    private lateinit var binding: FragmentAllDepartBinding
    private lateinit var viewModel: AllDepartViewModel
    private lateinit var adapter: AllDepartmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllDepartBinding.inflate(inflater)

        adapter = AllDepartmentAdapter(emptyList())
        binding.allDepartRecyclerView.adapter = adapter
        binding.allDepartRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            AllDepartViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[AllDepartViewModel::class.java]

        val managerId: Int? = arguments?.getInt("managerId")

        viewModel.departNonForManagerArray.observe(viewLifecycleOwner) { departs ->
            adapter.updateDepartments(departs)
        }

        managerId?.let { viewModel.getDepartmentsWithoutManager(it) }

        adapter.setOnItemClickListener(object : AllDepartmentAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val departmentId = viewModel.departNonForManagerArray.value?.get(position)?.id
                if (managerId != null && departmentId != null) {
                    viewModel.assignDepartmentToManager(managerId, departmentId)
                }
            }
        })

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
}