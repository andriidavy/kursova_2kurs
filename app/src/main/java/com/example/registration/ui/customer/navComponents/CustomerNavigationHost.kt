package com.example.registration.ui.customer.navComponents

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registration.ui.customer.cart.CustomerCartPageScreen
import com.example.registration.ui.customer.custom.CustomerCustomPageScreen
import com.example.registration.ui.customer.mainpage.CustomerMainPageScreen
import com.example.registration.ui.customer.navComponents.CustomerActions
import com.example.registration.ui.customer.navComponents.CustomerBottomNavItem
import com.example.registration.ui.customer.profile.CustomerProfilePageScreen

@Composable
fun CustomerNavigationHost(navController: NavHostController) {
    val actions = remember(navController) { CustomerActions(navController) }

    NavHost(navController = navController, startDestination = CustomerBottomNavItem.Home.route) {
        composable(CustomerBottomNavItem.Home.route){
            CustomerMainPageScreen()
        }
        composable(CustomerBottomNavItem.Cart.route){
            CustomerCartPageScreen()
        }
        composable(CustomerBottomNavItem.Orders.route){
            CustomerCustomPageScreen()
        }
        composable(CustomerBottomNavItem.Profile.route){
            CustomerProfilePageScreen()
        }
    }
}