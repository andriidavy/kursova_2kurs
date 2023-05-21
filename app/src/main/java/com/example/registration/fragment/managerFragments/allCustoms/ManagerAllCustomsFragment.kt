package com.example.registration.fragment.managerFragments.allCustoms

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
import com.example.registration.adapter.custom.ManagerAllCustomAdapter
import com.example.registration.adapter.custom.ManagerCreatedCustomAdapter
import com.example.registration.databinding.FragmentManagerAllCustomsBinding
import com.example.registration.model.custom.CustomProductDTO
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.allCustoms.ManagerAllCustomsPageViewModel
import com.example.registration.viewmodel.manager.allCustoms.ManagerAllCustomsPageViewModelFactory
import com.example.registration.viewmodel.manager.createdCustoms.ManagerCreatedCustomsPageViewModel
import com.example.registration.viewmodel.manager.createdCustoms.ManagerCreatedCustomsPageViewModelFactory


class ManagerAllCustomsFragment : Fragment() {
    private lateinit var binding: FragmentManagerAllCustomsBinding
    private lateinit var viewModel: ManagerAllCustomsPageViewModel
    private lateinit var adapter: ManagerAllCustomAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentManagerAllCustomsBinding.inflate(inflater)

        adapter = ManagerAllCustomAdapter(emptyList())
        binding.allCustomListRecyclerView.adapter = adapter
        binding.allCustomListRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            ManagerAllCustomsPageViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[ManagerAllCustomsPageViewModel::class.java]

        val navController = findNavController()

        viewModel.customAllArray.observe(viewLifecycleOwner) { customs ->
            adapter.updateCustoms(customs)
        }

        viewModel.getAllCustomsForManager()

        adapter.setOnItemClickListener(object : ManagerAllCustomAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //test
                Toast.makeText(activity, "Clicked on item $position", Toast.LENGTH_SHORT).show()
                val bundle = Bundle()
                val list: ArrayList<CustomProductDTO>? =
                    viewModel.customAllArray.value?.get(position)?.customProductList as ArrayList<CustomProductDTO>?
                bundle.putParcelableArrayList("allCustomProductList", list)
                navController.navigate(
                    R.id.action_managerAllCustomsFragment_to_allCustomsDetailFragment, bundle
                )
            }
        })


        return binding.root
    }

}