package com.example.registration.ui.user.mainpage

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.registration.R
import com.example.registration.databinding.FragmentUserMainPageBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class UserMainPageFragment : Fragment() {

    private lateinit var binding: FragmentUserMainPageBinding
    private lateinit var navController: NavController
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val viewModel by viewModels<UserMainPageViewModel>()
    private val LOCATION_PERMISSION_REQUEST_CODE = 101
    private var locationUpdateJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserMainPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setListeners()
    }

    private fun setupViews() {
        navController = findNavController()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    private fun setListeners() = with(binding) {
        navController.apply {

            buttonFileManage.setOnClickListener {
                navigate(R.id.action_userMainPageFragment_to_fileManageFragment)
            }


            buttonToProfile.setOnClickListener {
                navigate(R.id.action_userMainPageFragment_to_userProfilePageFragment)
            }

            swTurnMyLocation.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    if (locationUpdateJob == null) {
                        startLocationUpdates()
                    }
                } else {
                    stopLocationUpdates()
                }
            }

            btMyPlaces.setOnClickListener {
                navigate(R.id.action_userMainPageFragment_to_myPlacesFragment)
            }

            btMyFriends.setOnClickListener {
                navigate(R.id.action_userMainPageFragment_to_myFriendsFragment)
            }
        }
    }

    private fun startLocationUpdates() {
        locationUpdateJob = lifecycleScope.launch(Dispatchers.IO) {
            while (true) {
                try {
                    requestLocationPermission()
                    delay(60000) // Подождите 1 минуту перед следующим обновлением
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        // Обработайте ошибку обновления местоположения
                    }
                }
            }
        }
    }

    private fun stopLocationUpdates() {
        locationUpdateJob?.cancel()
        locationUpdateJob = null
    }

    private fun requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        val taskLocation = fusedLocationProviderClient.lastLocation

        taskLocation.addOnSuccessListener { location ->
            Log.e("Send location", "${location.latitude} ${location.longitude}")

            lifecycleScope.launch {
                viewModel.updateUserLocation(location).collect { result ->
                    result.onSuccess { returnLocation ->
                        Log.e(
                            "Return location", "$returnLocation"
                        )
                    }
                    result.onFailure {
                        Log.e("Return location", "failure")
                    }
                }
            }
        }
    }
}