package com.example.registration

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.backendless.Backendless
import com.example.registration.datastore.DataStoreViewModel
import com.example.registration.ui.user.profile.UserProfilePageViewModel
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

    override fun onSupportNavigateUp(): Boolean {
        val currentDestination = navController.currentDestination

        if (currentDestination?.id==R.id.registrationFragment){
            navController.navigate(R.id.loginFragment)
            return false
        }
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}