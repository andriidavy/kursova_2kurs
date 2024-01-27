package com.example.registration.ui.customer.navComponents

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Approval
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class CustomerBottomNavItem(val route: String, val icon: ImageVector, val label: String) {
    object Home : CustomerBottomNavItem("home", Icons.Default.Home, "Home")
    object Cart : CustomerBottomNavItem("cart", Icons.Default.ShoppingCart, "Cart")
    object Orders: CustomerBottomNavItem("orders", Icons.Default.Approval, "Orders")
    object Profile : CustomerBottomNavItem("profile", Icons.Default.Person, "Profile")

    companion object {
        fun values(): Array<CustomerBottomNavItem> = arrayOf(Home, Cart, Orders, Profile)
    }
}
