package com.example.registration.fragment.managerFragments.adminMode.managers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registration.adapter.manager.ManageManagerAdapter
import com.example.registration.databinding.FragmentEditManagerBinding
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.adminMode.EditManagerViewModel
import com.example.registration.viewmodel.manager.adminMode.EditManagerViewModelFactory


class EditManagerFragment : Fragment() {
    private lateinit var binding: FragmentEditManagerBinding
    private lateinit var viewModel: EditManagerViewModel
    private lateinit var adapter: ManageManagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditManagerBinding.inflate(inflater)

        adapter = ManageManagerAdapter(emptyList())
        binding.editManagerRecyclerView.adapter = adapter
        binding.editManagerRecyclerView.layoutManager = LinearLayoutManager(activity)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            EditManagerViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[EditManagerViewModel::class.java]

        val navController = findNavController()

//        val sharedManagerIdPreferences: SharedPreferences =
//            requireContext().getSharedPreferences("PrefsUserId", Context.MODE_PRIVATE)
//        viewModel.setSharedPreferences(sharedManagerIdPreferences)

        viewModel.getAllManagersProfileDTO()

        fun removeManager(managerId: Int) {
            viewModel.deleteManagerById(managerId)
        }

        adapter.setOnRemoveManagerClickListener(object :
            ManageManagerAdapter.OnRemoveManagerClickListener {
            override fun onRemoveManagerClick(position: Int) {
                val managerId : Int? = viewModel.managerAllArray.value?.get(position)?.id
                managerId?.let { removeManager(it) }
            }
        })


        return binding.root
    }
}