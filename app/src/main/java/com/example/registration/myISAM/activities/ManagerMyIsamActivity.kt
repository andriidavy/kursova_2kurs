package com.example.registration.myISAM.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.registration.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ManagerMyIsamActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manager_my_isam)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.manager_mi_nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }
}