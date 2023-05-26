package com.example.registration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.registration.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val currentDestination = navController.currentDestination
        if (currentDestination?.id == R.id.customerMainPageFragment ||
            currentDestination?.id == R.id.employeeMainPageFragment ||
            currentDestination?.id == R.id.managerMainPageFragment
        ) {
            return false
        }
        if (currentDestination?.id == R.id.managerCreatedCustomsPageFragment) {
            navController.navigate(R.id.managerMainPageFragment)
            return false
        }
        if (currentDestination?.id == R.id.addDepartForNewCustomFragment) {
            navController.navigate(R.id.managerMainPageFragment)
            return false
        }
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}