package com.example.registration.ui.manager.adminMode.managers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.manager.department.AllDepartmentAdapter
import com.example.registration.databinding.FragmentAllDepartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllDepartFragment : Fragment() {

    private lateinit var binding: FragmentAllDepartBinding
    private lateinit var adapter: AllDepartmentAdapter
    private val viewModel by viewModels<AllDepartViewModel>()
    private val managerId: Int? = arguments?.getInt("managerId")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllDepartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = AllDepartmentAdapter(emptyList(), onItemClick())
        binding.allDepartRecyclerView.adapter = adapter
        binding.allDepartRecyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.departNonForManagerArray.observe(viewLifecycleOwner) { departs ->
            adapter.updateDepartments(departs)
        }

        managerId?.let { viewModel.getDepartmentsWithoutManager(it) }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val departmentId = viewModel.departNonForManagerArray.value?.get(position)?.id
            if (managerId != null && departmentId != null) {
                viewModel.assignDepartmentToManager(managerId, departmentId)
            }
        }
    }
}