package com.example.registration.ui.myPlaces

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentMyPlacesBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyPlacesFragment : Fragment() {
    private lateinit var binding: FragmentMyPlacesBinding
    private lateinit var navController: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyPlacesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() = with(binding) {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        btAddPlace.setOnClickListener {
            navController.navigate(R.id.action_myPlacesFragment_to_addPlaceFragment)
        }
        btListOfPlace.setOnClickListener {
            navController.navigate(R.id.action_myPlacesFragment_to_listOfPlacesFragment)
        }
    }

}