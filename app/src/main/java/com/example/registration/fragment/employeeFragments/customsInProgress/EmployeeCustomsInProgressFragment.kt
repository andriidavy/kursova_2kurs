package com.example.registration.fragment.employeeFragments.customsInProgress

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
import com.example.registration.adapter.custom.EmployeeCustomInProgressAdapter
import com.example.registration.databinding.FragmentEmployeeCustomInProgressBinding
import com.example.registration.model.custom.CustomProductDTO
import com.example.registration.database.employee.EmployeeRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.employee.EmployeeApi
import com.example.registration.viewmodel.employee.customsInProgress.EmployeeCustomsInProgressViewModel
import com.example.registration.viewmodel.employee.customsInProgress.EmployeeCustomsInProgressViewModelFactory

class EmployeeCustomsInProgressFragment : Fragment() {
    private lateinit var binding: FragmentEmployeeCustomInProgressBinding
    private lateinit var viewModel: EmployeeCustomsInProgressViewModel
    private lateinit var adapter: EmployeeCustomInProgressAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentEmployeeCustomInProgressBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val employeeApi = retrofitService.retrofit.create(EmployeeApi::class.java)
        val employeeRepository = EmployeeRepository(employeeApi)
        val viewModelFactory =
            EmployeeCustomsInProgressViewModelFactory(employeeRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[EmployeeCustomsInProgressViewModel::class.java]

        val sharedEmployeeIdPreferences: SharedPreferences = requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedEmployeeIdPreferences)

        adapter = EmployeeCustomInProgressAdapter(emptyList())
        binding.employeeCustomInProgressRecyclerView.adapter = adapter
        binding.employeeCustomInProgressRecyclerView.layoutManager = LinearLayoutManager(activity)

        val navController = findNavController()

        viewModel.customInProgressArray.observe(viewLifecycleOwner) { customs ->
            adapter.updateCustoms(customs)
        }

        viewModel.getInProgressCustomsForEmployee()


        fun createReportToCustom(customId: Int) {
            val bundle = Bundle()
            bundle.putInt("customInProgressId", customId)
           navController.navigate(R.id.action_employeeCustomsInProgressFragment_to_creatingReportForCustomFragment, bundle)
        }

        adapter.setOnCreateReportClickListener(object :
            EmployeeCustomInProgressAdapter.OnCreateReportClickListener {
            override fun onCreateReportClick(customId: Int, position: Int) {
                createReportToCustom(customId)
            }
        })

        adapter.setOnItemClickListener(object : EmployeeCustomInProgressAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //test
                Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                val list: ArrayList<CustomProductDTO>? =
                    viewModel.customInProgressArray.value?.get(position)?.customProductList as ArrayList<CustomProductDTO>?
                bundle.putParcelableArrayList("customInProgressProductList", list)
                navController.navigate(R.id.action_employeeCustomsInProgressFragment_to_customInProgressProductDetailFragment, bundle)
            }
        })
        return binding.root
    }

}