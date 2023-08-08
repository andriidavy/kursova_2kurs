package com.example.registration.ui.customer.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.manager.department.AllDepartmentAdapter
import com.example.registration.databinding.FragmentAddDepartForNewCustomBinding

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddDepartForNewCustomFragment : Fragment() {

    private lateinit var binding: FragmentAddDepartForNewCustomBinding
    private lateinit var adapter: AllDepartmentAdapter
    private lateinit var navController: NavController
    private val viewModel by viewModels<AddDepartForNewCustomViewModel>()

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
        viewModel.departDTOArray.observe(viewLifecycleOwner) { departs ->
            adapter.updateDepartments(departs)
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun itemClicked(): (Int) -> Unit {
        return { position ->
            val departmentId = viewModel.departDTOArray.value?.getOrNull(position)?.id
            val customId = arguments?.getInt("customId")

            if (customId != null && departmentId != null) {
                viewModel.assignDepartmentToCustom(customId, departmentId)
                navController.navigate(R.id.action_addDepartForNewCustomFragment_to_customerMainPageFragment)
            }
        }
    }
}