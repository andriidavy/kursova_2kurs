package com.example.registration.ui.manager.adminMode.managers

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
import com.example.registration.adapter.manager.department.ManagerDepartmentAdapter
import com.example.registration.databinding.FragmentManagerDepartDetailBinding
import com.example.registration.global.ToastObj
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManagerDepartDetailFragment : Fragment() {

    private lateinit var binding: FragmentManagerDepartDetailBinding
    private lateinit var adapter: ManagerDepartmentAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<ManagerDepartDetailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerDepartDetailBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
        setObservers()
    }

    private fun setViews() = with(binding) {
        adapter = ManagerDepartmentAdapter(emptyList(), onRemoveManagerClick())
        managerDepartDetailRecyclerView.adapter = adapter
        managerDepartDetailRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        val managerId: Int = viewModel.managerId

        buttonAddDepart.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("managerId", managerId)
            navController.navigate(
                R.id.action_managerDepartDetailFragment_to_allDepartFragment,
                bundle
            )
        }
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.departForManagerArray.collect { departs ->
                    adapter.updateDepartments(departs)
                }
            }
        }
    }

    private fun onRemoveManagerClick(): (Int) -> Unit {
        return { position ->
            val departmentId: Int = viewModel.departForManagerArray.value[position].id
            val removeResult = viewModel.removeDepartmentFromManager(departmentId)
            lifecycleScope.launch {
                removeResult.collect { result ->
                    result.onSuccess {
                        viewModel.getAllDepartmentsForManager()
                    }
                    result.onFailure {
                        ToastObj.shortToastMake("Помилка: $it", context)
                    }
                }
            }
        }
    }
}