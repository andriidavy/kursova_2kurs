package com.example.registration.fragment.managerFragments.adminMode

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentAdminLoginBinding

class AdminLoginFragment : Fragment() {
    private lateinit var binding: FragmentAdminLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminLoginBinding.inflate(inflater)

        val navController = findNavController()

        binding.loginButton.setOnClickListener {
            if (binding.etPinCode.text.toString() == "4308") {
                navController.navigate(R.id.action_adminLoginFragment_to_adminMainPageFragment)
            }
            else {
                Toast.makeText(context,"Пін-код невірний!",Toast.LENGTH_LONG).show()
            }
        }




        return binding.root
    }

}