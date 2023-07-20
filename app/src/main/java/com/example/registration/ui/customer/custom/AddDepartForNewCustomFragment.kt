package com.example.registration.ui.customer.custom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.R
import com.example.registration.adapter.manager.department.AllDepartmentAdapter
import com.example.registration.databinding.FragmentAddDepartForNewCustomBinding
import com.example.registration.database.customer.CustomerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.customer.CustomerApi


class AddDepartForNewCustomFragment : Fragment() {
    private lateinit var binding: FragmentAddDepartForNewCustomBinding
    private lateinit var viewModel: AddDepartForNewCustomViewModel
    private lateinit var adapter: AllDepartmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddDepartForNewCustomBinding.inflate(inflater)

        adapter = AllDepartmentAdapter(emptyList())
        binding.departForNewCustomRecyclerView.adapter = adapter
        binding.departForNewCustomRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val customerApi = retrofitService.retrofit.create(CustomerApi::class.java)
        val customerRepository = CustomerRepository(customerApi)
        val viewModelFactory =
            AddDepartForNewCustomViewModelFactory(customerRepository)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[AddDepartForNewCustomViewModel::class.java]

        val navController = findNavController()

        viewModel.departDTOArray.observe(viewLifecycleOwner) { departs ->
            adapter.updateDepartments(departs)
        }

        val customId: Int? = arguments?.getInt("customId")

        viewModel.getAllDepartments()

        adapter.setOnItemClickListener(object : AllDepartmentAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val departmentId: Int? = viewModel.departDTOArray.value?.get(position)?.id
                if (departmentId != null && customId != null) {
                    viewModel.assignDepartmentToCustom(customId, departmentId)
                    navController.navigate(R.id.action_addDepartForNewCustomFragment_to_customerMainPageFragment)
                }
            }
        })

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        return binding.root
    }
}