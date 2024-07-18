package rs.ac.bg.etf.barberbooker.ui.elements

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
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
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberInitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientInitialScreen
import rs.ac.bg.etf.barberbooker.ui.stateholders.BarberBookerViewModel

@Composable
fun BarberBookerApp(
    barberBookerViewModel: BarberBookerViewModel = hiltViewModel()
) {
    val navHostController = rememberNavController()
    val context = LocalContext.current
    val barberBookerActivity = context as Activity?
    val uiState by barberBookerViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        barberBookerViewModel.updateStartDestination(context)
    }

    if (uiState.isInitialScreenLoading) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {}
    } else {
        Scaffold {
                paddingValues ->
            NavHost(
                navController = navHostController,
                startDestination = uiState.startDestination,
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
                    BackHandler {
                        barberBookerActivity?.finish()
                    }
                    barberBookerViewModel.updateLoginData(context, true, clientEmail, "client")
                    ClientInitialScreen(clientEmail)
                }
                composable(
                    route = "${staticRoutes[8]}/{barberEmail}",
                    arguments = listOf(
                        navArgument("barberEmail") {
                            type = NavType.StringType
                        }
                    )
                ) {navBackStackEntry ->
                    val barberEmail = navBackStackEntry.arguments?.getString("barberEmail") ?: ""
                    BackHandler {
                        barberBookerActivity?.finish()
                    }
                    barberBookerViewModel.updateLoginData(context, true, barberEmail, "barber")
                    BarberInitialScreen(barberEmail)
                }
            }
        }
    }
}