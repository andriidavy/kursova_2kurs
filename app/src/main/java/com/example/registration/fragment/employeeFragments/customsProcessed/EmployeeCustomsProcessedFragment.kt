package com.example.registration.fragment.employeeFragments.customsProcessed

import android.content.Context
import android.content.SharedPreferences
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
import com.example.registration.adapter.custom.EmployeeCustomProcessedAdapter
import com.example.registration.databinding.FragmentEmployeeCustomsProcessedBinding
import com.example.registration.model.custom.CustomProductDTO
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.employee.EmployeeApi
import com.example.registration.viewmodel.employee.customsProcessed.EmployeeCustomsProcessedViewModel
import com.example.registration.viewmodel.employee.customsProcessed.EmployeeCustomsProcessedViewModelFactory

class EmployeeCustomsProcessedFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeCustomsProcessedBinding
    private lateinit var viewModel: EmployeeCustomsProcessedViewModel
    private lateinit var adapter: EmployeeCustomProcessedAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmployeeCustomsProcessedBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val employeeApi = retrofitService.retrofit.create(EmployeeApi::class.java)
        val employeeRepository = EmployeeRepository(employeeApi)
        val viewModelFactory =
            EmployeeCustomsProcessedViewModelFactory(employeeRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[EmployeeCustomsProcessedViewModel::class.java]

        val sharedEmployeeIdPreferences: SharedPreferences = requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedEmployeeIdPreferences)

        adapter = EmployeeCustomProcessedAdapter(emptyList())
        binding.employeeCustomProcessedRecyclerView.adapter = adapter
        binding.employeeCustomProcessedRecyclerView.layoutManager = LinearLayoutManager(activity)

        val navController = findNavController()

        viewModel.customProcessedArray.observe(viewLifecycleOwner) { customs ->
            adapter.updateCustoms(customs)
        }

        viewModel.getProcessedCustomsForEmployee()


        adapter.setOnSendClickListener(object :
            EmployeeCustomProcessedAdapter.OnSendClickListener {
            override fun onSendClick(customId: Int, position: Int) {
                viewModel.setCustomSent(customId)
            }
        })

        adapter.setOnItemClickListener(object : EmployeeCustomProcessedAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //test
                Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                val list: ArrayList<CustomProductDTO>? =
                    viewModel.customProcessedArray.value?.get(position)?.customProductList as ArrayList<CustomProductDTO>?
                bundle.putParcelableArrayList("customProcessedProductList", list)
                navController.navigate(R.id.action_employeeCustomsProcessedFragment_to_customProcessedProductDetailFragment, bundle)
            }
        })

        return binding.root
    }
}