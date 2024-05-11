package com.example.registration.ui.myPlaces.addPlace

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentAddPlaceBinding
import com.example.registration.model.places.AddingPlaceDTO
import com.example.registration.model.users.data.LocationDTO
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddPlaceFragment : Fragment() {
    private lateinit var binding: FragmentAddPlaceBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<AddPlaceViewModel>()
    private lateinit var userLocation: LocationDTO
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPlaceBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() {
        navController = findNavController()
    }

    private fun setListeners() = with(binding) {
        btAddMyLocation.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getMyLocation().collect { result ->
                    result.onSuccess { response ->
                        userLocation = response[0].location

                        tvLocation.text = userLocation.coordinates.toString()
                        Log.e("GettingMyLocation:", "$response")
                    }
                    result.onFailure {
                        Log.e("GettingMyLocation:", "failure")
                    }
                }
            }
        }

        btConfirmingNewPlace.setOnClickListener {
            val description = etPlaceDescription.text.toString()
            lifecycleScope.launch {
                viewModel.addPlace(description, userLocation).collect { result ->
                    result.onSuccess { addingPlaceDTO ->
                        Log.e("addPlace", "$addingPlaceDTO")
                    }
                    result.onFailure { Log.e("addPlace", "failure") }
                }
            }
        }
    }
}