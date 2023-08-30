package com.example.registration.ui.manager.adminMode.managers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.registration.databinding.FragmentAddManagerBinding
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.manager.ManagerApi

class AddManagerFragment : Fragment() {
    private lateinit var binding: FragmentAddManagerBinding
    private lateinit var viewModel: AddManagerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddManagerBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            AddManagerViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[AddManagerViewModel::class.java]

        binding.buttonAdd.setOnClickListener {
            val name: String = binding.etName.text.toString()
            val surname: String = binding.etSurname.text.toString()
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            if (name.isNotEmpty() && surname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.addManager(name, surname, email, password)
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
