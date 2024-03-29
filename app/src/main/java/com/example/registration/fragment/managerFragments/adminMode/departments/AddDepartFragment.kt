package com.example.registration.fragment.managerFragments.adminMode.departments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.registration.R
import com.example.registration.databinding.FragmentAddDepartBinding
import com.example.registration.repository.ManagerRepository
import com.example.registration.retrofit.RetrofitService
import com.example.registration.retrofit.managerApi.ManagerApi
import com.example.registration.viewmodel.manager.adminMode.editDeparts.AddDepartsViewModel
import com.example.registration.viewmodel.manager.adminMode.editDeparts.AddDepartsViewModelFactory
import com.example.registration.viewmodel.manager.adminMode.editDeparts.EditDepartsViewModel
import com.example.registration.viewmodel.manager.adminMode.editDeparts.EditDepartsViewModelFactory


class AddDepartFragment : Fragment() {
private lateinit var binding: FragmentAddDepartBinding
private lateinit var viewModel: AddDepartsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDepartBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            AddDepartsViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[AddDepartsViewModel::class.java]

        binding.buttonAdd.setOnClickListener {
            val departmentName = binding.etName.text.toString()
            if(departmentName.isNotEmpty()) {
                viewModel.saveDepart(departmentName)
                binding.etName.text.clear()
            } else {
                viewModel.message.value = "Всі поля мають бути заповнені!"
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

}