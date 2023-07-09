package com.example.registration.fragment.managerFragments.personManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.registration.databinding.FragmentAddEmployeeBinding
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.manager.ManagerApi
import com.example.registration.viewmodel.manager.personManagement.AddEmployeeViewModel
import com.example.registration.viewmodel.manager.personManagement.AddEmployeeViewModelFactory


class AddEmployeeFragment : Fragment() {
    private lateinit var binding: FragmentAddEmployeeBinding
    private lateinit var viewModel: AddEmployeeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEmployeeBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            AddEmployeeViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[AddEmployeeViewModel::class.java]

        binding.buttonAdd.setOnClickListener {
            val name: String = binding.etName.text.toString()
            val surname: String = binding.etSurname.text.toString()
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            if (name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.addEmployee(name, surname, email, password)
                binding.etName.text.clear()
                binding.etSurname.text.clear()
                binding.etEmail.text.clear()
                binding.etPassword.text.clear()
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