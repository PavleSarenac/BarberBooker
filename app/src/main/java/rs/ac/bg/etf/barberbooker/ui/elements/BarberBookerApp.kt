package rs.ac.bg.etf.barberbooker.ui.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.InitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInAsBarberScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInAsClientScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpAsBarberScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpAsClientScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientInitialScreen

@Composable
fun BarberBookerApp() {
    val navHostController = rememberNavController()

    Scaffold {
        paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = staticRoutes[0],
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = staticRoutes[0]) {
                InitialScreen(navHostController)
            }
            composable(route = staticRoutes[1]) {
                LogInScreen(navHostController)
            }
            composable(route = staticRoutes[2]) {
                SignUpScreen(navHostController)
            }
            composable(route = staticRoutes[3]) {
                SignUpAsClientScreen(navHostController)
            }
            composable(route = staticRoutes[4]) {
                SignUpAsBarberScreen(navHostController)
            }
            composable(route = staticRoutes[5]) {
                LogInAsClientScreen(navHostController)
            }
            composable(route = staticRoutes[6]) {
                LogInAsBarberScreen(navHostController)
            }
            composable(
                route = "${staticRoutes[7]}/{clientEmail}",
                arguments = listOf(
                    navArgument("clientEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: ""
                ClientInitialScreen(clientEmail)
            }
        }
    }
}