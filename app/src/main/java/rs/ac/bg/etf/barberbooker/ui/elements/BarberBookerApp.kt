package rs.ac.bg.etf.barberbooker.ui.elements

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.InitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.LogInScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.SignUpAsBarberScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.SignUpAsClientScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.SignUpScreen

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
                LogInScreen()
            }
            composable(route = staticRoutes[2]) {
                SignUpScreen(navHostController)
            }
            composable(route = staticRoutes[3]) {
                SignUpAsClientScreen(navHostController)
            }
            composable(route = staticRoutes[4]) {
                SignUpAsBarberScreen()
            }
        }
    }
}