package com.example.registration.ui.customer.navComponents

import androidx.navigation.NavHostController

class CustomerActions(navHostController: NavHostController) {
    val navigateBack: () -> Unit = {
        navHostController.popBackStack()
    }
}