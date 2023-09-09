package com.example.registration.ui.employee.customsProcessed

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
import com.example.registration.adapter.custom.EmployeeCustomProcessedAdapter
import com.example.registration.databinding.FragmentEmployeeCustomsProcessedBinding
import com.example.registration.model.custom.CustomProductDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EmployeeCustomsProcessedFragment : Fragment() {

    private lateinit var binding: FragmentEmployeeCustomsProcessedBinding
    private lateinit var adapter: EmployeeCustomProcessedAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<EmployeeCustomsProcessedViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmployeeCustomsProcessedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setObservers()
    }

    private fun setViews() = with(binding){
        adapter = EmployeeCustomProcessedAdapter(emptyList(), onSendClick(), onItemClick())
        employeeCustomProcessedRecyclerView.adapter = adapter
        employeeCustomProcessedRecyclerView.layoutManager = LinearLayoutManager(activity)

        navController = findNavController()
    }

    private fun setObservers() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.customProcessedArray.collect { customs ->
                    adapter.updateCustoms(customs)
                }
            }
        }
    }

    private fun onSendClick(): (Int) -> Unit {
        return { customId ->
            viewModel.setCustomSent(customId)
        }
    }

    private fun onItemClick(): (Int) -> Unit {
        return { position ->
            val bundle = Bundle()
            val list: ArrayList<CustomProductDTO>? =
                viewModel.customProcessedArray.value[position].customProductList as ArrayList<CustomProductDTO>?
            bundle.putParcelableArrayList("customProcessedProductList", list)
            navController.navigate(
                R.id.action_employeeCustomsProcessedFragment_to_customProcessedProductDetailFragment,
                bundle
            )
        }
    }
}