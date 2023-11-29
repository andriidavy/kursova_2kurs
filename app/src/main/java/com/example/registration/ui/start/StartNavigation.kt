package com.example.registration.ui.start

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registration.ui.start.customerRegistration.CustomerRegistrationScreen
import com.example.registration.ui.start.login.LoginScreen
import com.example.registration.ui.start.navComponents.StartActions
import com.example.registration.ui.start.navComponents.StartDestinations

@Composable
fun StartNavigation() {
    val navController = rememberNavController()
    val actions = remember(navController) { StartActions(navController) }

    NavHost(navController = navController, startDestination = StartDestinations.Login) {
        composable(StartDestinations.Login) {
            LoginScreen(openCustomerRegistration = actions.openCustomerRegistration)
        }
        composable(StartDestinations.CustomerRegistration) {
            CustomerRegistrationScreen(navigateBack = actions.navigateBack)
        }
    }
}