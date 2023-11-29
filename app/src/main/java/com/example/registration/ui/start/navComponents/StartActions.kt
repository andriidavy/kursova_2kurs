package com.example.registration.ui.start.navComponents

import androidx.navigation.NavHostController

class StartActions(navHostController: NavHostController) {
    val openLogin: () -> Unit = {
        navHostController.navigate(StartDestinations.Login)
    }
    val openCustomerRegistration: () -> Unit = {
        navHostController.navigate(StartDestinations.CustomerRegistration)
    }
    val navigateBack: ()->Unit = {
        navHostController.popBackStack()
    }
}