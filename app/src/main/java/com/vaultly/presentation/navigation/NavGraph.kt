package com.vaultly.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vaultly.presentation.analytics.AnalyticsScreen
import com.vaultly.presentation.auth.AuthScreen
import com.vaultly.presentation.budget.BudgetScreen
import com.vaultly.presentation.dashboard.DashboardScreen
import com.vaultly.presentation.settings.SettingsScreen
import com.vaultly.presentation.transactions.TransactionsScreen

sealed class VaultlyRoute(val route: String) {
    data object Auth : VaultlyRoute("auth")
    data object Dashboard : VaultlyRoute("dashboard")
    data object Transactions : VaultlyRoute("transactions")
    data object Analytics : VaultlyRoute("analytics")
    data object Budgets : VaultlyRoute("budgets")
    data object Settings : VaultlyRoute("settings")
}

@Composable
fun VaultlyNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = VaultlyRoute.Auth.route,
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable(VaultlyRoute.Auth.route) {
            AuthScreen(
                onAuthenticated = {
                    navController.navigate(VaultlyRoute.Dashboard.route) {
                        popUpTo(VaultlyRoute.Auth.route) { inclusive = true }
                    }
                }
            )
        }
        composable(VaultlyRoute.Dashboard.route) { DashboardScreen() }
        composable(VaultlyRoute.Transactions.route) { TransactionsScreen() }
        composable(VaultlyRoute.Analytics.route) { AnalyticsScreen() }
        composable(VaultlyRoute.Budgets.route) { BudgetScreen() }
        composable(VaultlyRoute.Settings.route) { SettingsScreen() }
    }
}
