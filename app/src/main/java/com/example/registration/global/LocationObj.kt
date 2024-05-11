package com.example.registration.global

import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.registration.model.users.data.LocationDTO

//object LocationObj {
//
//
//    private fun getLastLocation() {
//        fusedLocationClient.lastLocation
//            .addOnSuccessListener { location: Location? ->
//                // Обработайте полученное местоположение
//                location?.let {
//                    val latitude = it.latitude
//                    val longitude = it.longitude
//                    val locationDTO = LocationDTO(latitude, longitude)
//                    updateUserLocation(locationDTO)
//                }
//            }
//            .addOnFailureListener { e ->
//                // Обработайте ошибку при получении местоположения
//                Log.e(TAG, "Error getting location: ${e.message}")
//            }
//    }
//}