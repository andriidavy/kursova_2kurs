package com.example.registration.global

import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.registration.model.users.data.LocationDTO
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

object LocationObj {

    fun roundToDecimals(number: Double, numDecimalPlaces: Int): Double {
        val factor = 10.0.pow(numDecimalPlaces)
        return kotlin.math.round(number * factor) / factor
    }

    fun haversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Радиус Земли в километрах

        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)

        val a =
            sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(
                2
            )
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))

        return R * c
    }
}