package com.example.registration.fragment.managerFragments.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.registration.databinding.FragmentAddProductBinding
import com.example.registration.database.manager.ManagerRepository
import com.example.registration.database.RetrofitService
import com.example.registration.database.manager.ManagerApi
import com.example.registration.viewmodel.manager.products.AddProductViewModel
import com.example.registration.viewmodel.manager.products.AddProductViewModelFactory

class AddProductFragment : Fragment() {
    private lateinit var binding: FragmentAddProductBinding
    private lateinit var viewModel: AddProductViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddProductBinding.inflate(inflater)

        val retrofitService = RetrofitService()
        val managerApi = retrofitService.retrofit.create(ManagerApi::class.java)
        val managerRepository = ManagerRepository(managerApi)
        val viewModelFactory =
            AddProductViewModelFactory(managerRepository)
        viewModel =
            ViewModelProvider(
                this,
                viewModelFactory
            )[AddProductViewModel::class.java]

        binding.addProductButton.setOnClickListener {
            val name: String = binding.productName.text.toString()
            val quantity: Int = if (binding.productQuantity.text.isNotEmpty()) {
                Integer.parseInt(binding.productQuantity.text.toString())
            } else { 0 }
            val description: String = binding.productDescription.text.toString()

            if (name.isNotEmpty() && quantity > 0) {
                viewModel.saveProduct(name, description, quantity)
            } else {
                viewModel.message.value = "поля name та quantity мають бути заповнені!"
            }
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }



        return binding.root
    }

}