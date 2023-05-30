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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.registrationFragment -> supportActionBar?.title = "Реєстрація нового покупця"
                R.id.loginFragment -> supportActionBar?.title = "Сторінка авторизації"
                R.id.customerMainPageFragment -> supportActionBar?.title = "Головна сторінка покупця"
                R.id.customerCartPageFragment -> supportActionBar?.title = "Моя корзина"
                R.id.customerCustomPageFragment -> supportActionBar?.title = "Мої замовлення"
                R.id.customerProfilePageFragment -> supportActionBar?.title = "Мій профіль"
                R.id.customProductDetailFragment -> supportActionBar?.title = "Деталі замовлення"
                R.id.productItemFragment -> supportActionBar?.title = "Деталі товару"

                R.id.employeeMainPageFragment -> supportActionBar?.title = "Головна сторінка робітника"
                R.id.employeeCustomsInProgressFragment -> supportActionBar?.title = "Призначені замовлення"
                R.id.employeeCustomsProcessedFragment -> supportActionBar?.title = "Виконані замовлення"
                R.id.employeeReportsRejectedFragment -> supportActionBar?.title = "Відхилені звіти"
                R.id.employeeReportsAcceptedFragment -> supportActionBar?.title = "Прийняті звіти"
                R.id.employeeReportsInWaitingFragment -> supportActionBar?.title = "Звіти в очікуванні"
                R.id.reportDetailFragment -> supportActionBar?.title = "Деталі звіту"
                R.id.employeeProfilePageFragment -> supportActionBar?.title = "Мій профіль"
                R.id.customInProgressProductDetailFragment -> supportActionBar?.title = "Деталі замовлення"
                R.id.customProcessedProductDetailFragment -> supportActionBar?.title = "Деталі замовлення"
                
                R.id.managerMainPageFragment -> supportActionBar?.title = "Головна сторінка менеджера"
                R.id.managerCreatedCustomsPageFragment -> supportActionBar?.title = "Нові замовлення"
                R.id.createdCustomsProductDetailFragment -> supportActionBar?.title = "Деталі замовлення"
                R.id.employeesListForAssigneeToCustomFragment -> supportActionBar?.title = "Обрати робітника"
                R.id.managerReportsInWaitingFragment -> supportActionBar?.title = "Нові звіти"
                R.id.managerReportsInWaitingDetailFragment -> supportActionBar?.title = "Деталі звіту"
                R.id.managerAllCustomsFragment -> supportActionBar?.title = "Історія всіх замовлень"
                R.id.allCustomsDetailFragment -> supportActionBar?.title = "Деталі замовлення"
                R.id.allProductListFragment -> supportActionBar?.title = "Список товарів"
                R.id.addProductFragment -> supportActionBar?.title = "Додати товар"
                R.id.manageEmployeeFragment -> supportActionBar?.title = "Список робітників"
                R.id.addEmployeeFragment -> supportActionBar?.title = "Додати робітника"
                R.id.managerProfilePageFragment -> supportActionBar?.title = "Мій профіль"
                R.id.adminLoginFragment -> supportActionBar?.title = "Авторизація адміністратора"
                R.id.adminMainPageFragment -> supportActionBar?.title = "Головна сторінка адміністратора"
                R.id.editManagerFragment -> supportActionBar?.title = "Список менеджерів"
                R.id.addManagerFragment -> supportActionBar?.title = "Додати менеджера"
                R.id.managerDepartDetailFragment -> supportActionBar?.title = "Призначені відділи"
                R.id.editDepartsFragment -> supportActionBar?.title = "Список відділів доставки"
                R.id.addDepartFragment -> supportActionBar?.title = "Додати відділ доставки"
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val currentDestination = navController.currentDestination
        if (currentDestination?.id == R.id.customerMainPageFragment ||
            currentDestination?.id == R.id.employeeMainPageFragment ||
            currentDestination?.id == R.id.managerMainPageFragment ||
            currentDestination?.id == R.id.addDepartForNewCustomFragment
        ) {
            return false
        }
        if (currentDestination?.id == R.id.managerCreatedCustomsPageFragment) {
            navController.navigate(R.id.managerMainPageFragment)
            return false
        }
        if (currentDestination?.id==R.id.registrationFragment){
            navController.navigate(R.id.loginFragment)
            return false
        }
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}