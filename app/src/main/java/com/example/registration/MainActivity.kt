package com.example.registration

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.backendless.Backendless
import com.backendless.async.callback.AsyncCallback
import com.backendless.exceptions.BackendlessFault
import com.backendless.push.DeviceRegistrationResult
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Backendless.initApp(applicationContext, "FF1A5A1D-9D50-49DE-FF52-3A572F090300", "D9A001DE-5CCD-4424-A5D9-E721278818E6")
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.registrationFragment -> supportActionBar?.title = "Реєстрація нового покупця"
                R.id.loginFragment -> supportActionBar?.title = "Сторінка авторизації"
            }
        }
    }

    private fun registerDevice() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val deviceToken = task.result
                val channels: MutableList<String> = ArrayList()
                channels.add("default")
                Backendless.Messaging.registerDevice(
                    channels,
                    object : AsyncCallback<DeviceRegistrationResult?> {
                        override fun handleResponse(response: DeviceRegistrationResult?) {
                            Toast.makeText(applicationContext, "Device registered!", Toast.LENGTH_LONG).show()
                        }

                        override fun handleFault(fault: BackendlessFault) {
                            Toast.makeText(applicationContext, "Error registering " + fault.message, Toast.LENGTH_LONG).show()
                        }
                    }
                )
            } else {
                Toast.makeText(applicationContext, "Error getting device token", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val currentDestination = navController.currentDestination

        if (currentDestination?.id==R.id.registrationFragment){
            navController.navigate(R.id.loginFragment)
            return false
        }
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}