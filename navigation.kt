package vcmsa.projects.expensetracker

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ExpenseTrackerNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                onNavigateToAddExpense = { navController.navigate("add_expense") },
                onNavigateToTransactions = { navController.navigate("transactions") },
                onNavigateToAnalytics = { navController.navigate("analytics") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        composable("add_expense") {
            AddExpenseScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("transactions") {
            TransactionsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("analytics") {
            AnalyticsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("profile") {
            ProfileScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
