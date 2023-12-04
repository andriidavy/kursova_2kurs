package com.example.registration.ui.manager.adminMode.departments

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
import com.example.registration.adapter.manager.department.AllDepartmentAdapter
import com.example.registration.adapter.manager.department.ManagerDepartmentAdapter
import com.example.registration.databinding.FragmentEditDepartsBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditDepartsFragment : Fragment() {

    private lateinit var binding: FragmentEditDepartsBinding
    private lateinit var adapter: AllDepartmentAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<EditDepartsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditDepartsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
        setObservers()
    }

    private fun setViews() = with(binding){
        adapter = AllDepartmentAdapter(emptyList(), onItemClick())
        editDepartsRecyclerView.adapter = adapter
        editDepartsRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.departAllArray.collect { departs ->
                    adapter.updateDepartments(departs)
                }
            }
        }
    }

    private fun setListeners() = with(binding) {
        buttonToAddDeparts.setOnClickListener {
            navController.navigate(R.id.action_editDepartsFragment_to_addDepartFragment)
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val departmentId: Int = viewModel.departAllArray.value[position].id
            val departmentName: String = viewModel.departAllArray.value[position].departmentName
            ToastObj.longToastMake(getString(R.string.depart_message, departmentName, departmentId), context)
        }
    }
}