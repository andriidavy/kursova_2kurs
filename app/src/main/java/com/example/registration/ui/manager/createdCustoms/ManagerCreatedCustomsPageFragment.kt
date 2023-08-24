package com.example.registration.ui.manager.createdCustoms

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.custom.ManagerCreatedCustomAdapter
import com.example.registration.databinding.FragmentManagerCreatedCustomsPageBinding
import com.example.registration.model.custom.CustomProductDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ManagerCreatedCustomsPageFragment : Fragment() {

    private lateinit var binding: FragmentManagerCreatedCustomsPageBinding
    private lateinit var adapter: ManagerCreatedCustomAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<ManagerCreatedCustomsPageViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentManagerCreatedCustomsPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews() {
        adapter = ManagerCreatedCustomAdapter(emptyList(), onAssignEmployeeClick(), onItemClick())
        binding.createdCustomListRecyclerView.adapter = adapter
        binding.createdCustomListRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customCreatedArray.collect { customs ->
                    adapter.updateCustoms(customs)
                }
            }
        }
    }

    private fun onAssignEmployeeClick(): (Int) -> Unit {
        return { customId ->
            val bundle = Bundle()
            bundle.putInt("customId", customId)
            navController.navigate(
                R.id.action_managerCreatedCustomsPageFragment_to_employeesListForAssigneeToCustomFragment,
                bundle
            )
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val list: ArrayList<CustomProductDTO>? =
                viewModel.customCreatedArray.value[position].customProductList as ArrayList<CustomProductDTO>?
            bundle.putParcelableArrayList("createdCustomProductList", list)
            navController.navigate(
                R.id.action_managerCreatedCustomsPageFragment_to_createdCustomsProductDetailFragment,
                bundle
            )
        }
    }
}