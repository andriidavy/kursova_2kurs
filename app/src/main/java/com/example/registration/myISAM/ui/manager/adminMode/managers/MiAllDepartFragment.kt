package com.example.registration.myISAM.ui.manager.adminMode.managers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.manager.department.AllDepartmentAdapter
import com.example.registration.databinding.FragmentAllDepartBinding
import com.example.registration.global.ToastObj
import com.example.registration.ui.manager.adminMode.managers.AllDepartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiAllDepartFragment : Fragment() {

    private lateinit var binding: FragmentAllDepartBinding
    private lateinit var adapter: AllDepartmentAdapter
    private val viewModel by viewModels<MiAllDepartViewModel>()
    private var managerId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllDepartBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() = with(binding) {
        adapter = AllDepartmentAdapter(emptyList(), onItemClick())
        allDepartRecyclerView.adapter = adapter
        allDepartRecyclerView.layoutManager = LinearLayoutManager(activity)
        managerId = arguments?.getInt("managerId") ?: 0

        viewModel.getDepartmentsWithoutManager(managerId)
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.departNonForManagerArray.collect { departs ->
                    adapter.updateDepartments(departs)
                }
            }
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val departmentId = viewModel.departNonForManagerArray.value[position].id
            val assignResult = viewModel.assignDepartmentToManager(managerId, departmentId)

            lifecycleScope.launch {
                assignResult.collect { result ->
                    result.onSuccess {
                        ToastObj.shortToastMake("Відділ призначено менеджеру успішно", context)
                        viewModel.getDepartmentsWithoutManager(managerId)
                    }
                    result.onFailure {
                        ToastObj.shortToastMake("Помилка: $it", context)
                    }
                }
            }
        }
    }
}