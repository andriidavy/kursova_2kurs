package com.example.registration.fragment.managerFragments.createdCustoms

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
import com.example.registration.adapter.custom.CustomAdapter
import com.example.registration.adapter.custom.ManagerCreatedCustomAdapter
import com.example.registration.databinding.FragmentManagerCreatedCustomsPageBinding
import com.example.registration.model.custom.CustomProductDTO
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.createdCustoms.ManagerCreatedCustomsPageViewModel
import com.example.registration.viewmodel.manager.createdCustoms.ManagerCreatedCustomsPageViewModelFactory

class ManagerCreatedCustomsPageFragment : Fragment() {
    private lateinit var binding: FragmentManagerCreatedCustomsPageBinding
    private lateinit var viewModel: ManagerCreatedCustomsPageViewModel
    private lateinit var adapter: ManagerCreatedCustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerCreatedCustomsPageBinding.inflate(inflater)

        adapter = ManagerCreatedCustomAdapter(emptyList())
        binding.createdCustomListRecyclerView.adapter = adapter
        binding.createdCustomListRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            ManagerCreatedCustomsPageViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[ManagerCreatedCustomsPageViewModel::class.java]

        val navController = findNavController()

        viewModel.customCreatedArray.observe(viewLifecycleOwner) { customs ->
            adapter.updateCustoms(customs)
        }

        viewModel.getCreatedCustomsForManager()

        val sharedManagerIdPreferences: SharedPreferences =
            requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
        viewModel.setSharedPreferences(sharedManagerIdPreferences)

        fun assignEmployeeToCustom(customId: Int) {
            val bundle = Bundle()
            bundle.putInt("customId", customId)
            navController.navigate(
                R.id.action_managerCreatedCustomsPageFragment_to_employeesListForAssigneeToCustomFragment,
                bundle
            )
        }

        adapter.setOnAssignEmployeeClickListener(object :
            ManagerCreatedCustomAdapter.OnAssignEmployeeClickListener {
            override fun onAssignEmployeeClick(customId: Int, position: Int) {
                assignEmployeeToCustom(customId)
            }
        })

        adapter.setOnItemClickListener(object : ManagerCreatedCustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //test
                Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                val list: ArrayList<CustomProductDTO>? =
                    viewModel.customCreatedArray.value?.get(position)?.customProductList as ArrayList<CustomProductDTO>?
                bundle.putParcelableArrayList("createdCustomProductList", list)
                navController.navigate(
                    R.id.action_managerCreatedCustomsPageFragment_to_createdCustomsProductDetailFragment,
                    bundle
                )
            }
        })


        return binding.root
    }

}