package com.example.registration.myISAM.ui.customer.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.manager.department.AllDepartmentAdapter
import com.example.registration.databinding.FragmentAddDepartForNewCustomBinding
import com.example.registration.global.ToastObj
import com.example.registration.ui.customer.custom.AddDepartForNewCustomViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MiAddDepartForNewCustomFragment : Fragment() {
    private lateinit var binding: FragmentAddDepartForNewCustomBinding
    private lateinit var adapter: AllDepartmentAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<MiAddDepartForNewCustomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddDepartForNewCustomBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setObservers()
    }

    private fun setupViews() = with(binding) {
        adapter = AllDepartmentAdapter(emptyList(), itemClicked())
        departForNewCustomRecyclerView.adapter = adapter
        departForNewCustomRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.departDTOArray.collect { departs ->
                    adapter.updateDepartments(departs)
                }
            }
        }
    }

    private fun itemClicked(): (Int) -> Unit {
        return { position ->
            val departmentId = viewModel.departDTOArray.value.getOrNull(position)?.id

            departmentId?.let {
                lifecycleScope.launch {
                    viewModel.createCustom(departmentId).collect { result ->
                        result.onSuccess { customId ->
                            ToastObj.longToastMake(
                                getString(
                                    R.string.success_create_custom,
                                    customId
                                ), context
                            )
                        }
                        result.onFailure {
                            ToastObj.shortToastMake(
                                getString(R.string.wrong_create_custom),
                                context
                            )
                        }
                    }
                }
                navController.navigate(R.id.action_miAddDepartForNewCustomFragment_to_miCustomerMainPageFragment)
            }
        }
    }
}