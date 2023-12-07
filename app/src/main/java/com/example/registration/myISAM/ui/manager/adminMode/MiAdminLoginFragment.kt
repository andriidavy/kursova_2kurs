package com.example.registration.myISAM.ui.manager.adminMode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentAdminLoginBinding
import com.example.registration.global.ToastObj

class MiAdminLoginFragment : Fragment() {

    private lateinit var binding: FragmentAdminLoginBinding
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAdminLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
        setListeners()
    }

    private fun setViews() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        loginButton.setOnClickListener {
            if (etPinCode.text.toString() == "4308") {
                navController.navigate(R.id.action_miAdminLoginFragment_to_miAdminMainPageFragment)
            } else {
                ToastObj.shortToastMake(getString(R.string.invalid_pin), context)
            }
        }
    }
}