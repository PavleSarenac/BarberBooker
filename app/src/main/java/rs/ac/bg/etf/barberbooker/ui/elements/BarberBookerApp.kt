package rs.ac.bg.etf.barberbooker.ui.elements

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.elements.composables.guest.topbars.GuestTopBar
import rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber.BarberBottomNavigationBar
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.InitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInAsBarberScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInAsClientScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpAsBarberScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpAsClientScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberAccountScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberInitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientInitialScreen
import rs.ac.bg.etf.barberbooker.ui.stateholders.BarberBookerViewModel

@Composable
fun BarberBookerApp(
    barberBookerViewModel: BarberBookerViewModel = hiltViewModel()
) {
    val navHostController = rememberNavController()
    val currentBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route ?: staticRoutes[0]

    val context = LocalContext.current
    val barberBookerActivity = context as Activity?
    val uiState by barberBookerViewModel.uiState.collectAsState()

    val snackbarHostStates = remember { List(4) { SnackbarHostState() } }

    LaunchedEffect(Unit) {
        barberBookerViewModel.updateStartDestination(context)
    }

    if (uiState.isInitialScreenLoading) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxSize()
        ) {}
    } else {
        Scaffold(
            topBar = {
                ScaffoldTopBar(currentRoute, navHostController)
            },
            bottomBar = {
                ScaffoldBottomBar(currentRoute, navHostController)
            },
            snackbarHost = {
                ScaffoldSnackbarHost(currentRoute, snackbarHostStates)
            }
        ) { paddingValues ->
            NavHost(
                navController = navHostController,
                startDestination = uiState.startDestination,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable(route = staticRoutes[0]) {
                    InitialScreen(navHostController)
                }
                composable(route = staticRoutes[1]) {
                    LogInScreen(navHostController, paddingValues)
                }
                composable(route = staticRoutes[2]) {
                    SignUpScreen(navHostController, paddingValues)
                }
                composable(route = staticRoutes[3]) {
                    SignUpAsClientScreen(navHostController, paddingValues, snackbarHostStates[0])
                }
                composable(route = staticRoutes[4]) {
                    SignUpAsBarberScreen(navHostController, paddingValues, snackbarHostStates[1])
                }
                composable(route = staticRoutes[5]) {
                    LogInAsClientScreen(navHostController, paddingValues, snackbarHostStates[2])
                }
                composable(route = staticRoutes[6]) {
                    LogInAsBarberScreen(navHostController, paddingValues, snackbarHostStates[3])
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
                    val previousRoute = navHostController.previousBackStackEntry?.destination?.route
                    if (previousRoute == staticRoutes[5]) {
                        barberBookerViewModel.updateLoginData(context, true, clientEmail, "client")
                    }
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
                    val previousRoute = navHostController.previousBackStackEntry?.destination?.route
                    if (previousRoute == staticRoutes[6]) {
                        barberBookerViewModel.updateLoginData(context, true, barberEmail, "barber")
                    }
                    BarberInitialScreen(barberEmail, navHostController)
                }
                composable(
                    route = "${staticRoutes[9]}/{barberEmail}",
                    arguments = listOf(
                        navArgument("barberEmail") {
                            type = NavType.StringType
                        }
                    )
                ) {navBackStackEntry ->
                    val barberEmail = navBackStackEntry.arguments?.getString("barberEmail") ?: ""
                    BarberAccountScreen(barberEmail, navHostController)
                }
            }
        }
    }
}

@Composable
fun ScaffoldTopBar(currentRoute: String, navHostController: NavHostController) {
    when (currentRoute) {
        staticRoutes[1] -> GuestTopBar(
            topBarTitle = "Log in to BarberBooker",
            navHostController = navHostController
        )
        staticRoutes[2] -> GuestTopBar(
            topBarTitle = "Sign up for BarberBooker",
            navHostController = navHostController
        )
        staticRoutes[3] -> GuestTopBar(
            topBarTitle = "Sign up as a client",
            navHostController = navHostController
        )
        staticRoutes[4] -> GuestTopBar(
            topBarTitle = "Sign up as a barber",
            navHostController = navHostController
        )
        staticRoutes[5] -> GuestTopBar(
            topBarTitle = "Log in as a client",
            navHostController = navHostController
        )
        staticRoutes[6] -> GuestTopBar(
            topBarTitle = "Log in as a barber",
            navHostController = navHostController
        )
        else -> {}
    }
}

@Composable
fun ScaffoldBottomBar(currentRoute: String, navHostController: NavHostController) {
    if (currentRoute.contains("Barber") && currentRoute.contains("/")) {
        val barberEmail = currentRoute.substring(currentRoute.indexOf("/") + 1)
        BarberBottomNavigationBar(barberEmail, navHostController)
    }
}

@Composable
fun ScaffoldSnackbarHost(currentRoute: String, snackbarHostStates: List<SnackbarHostState>) {
    when (currentRoute) {
        staticRoutes[3] -> {
            SnackbarHost(hostState = snackbarHostStates[0])
        }
        staticRoutes[4] -> {
            SnackbarHost(hostState = snackbarHostStates[1])
        }
        staticRoutes[5] -> {
            SnackbarHost(hostState = snackbarHostStates[2])
        }
        staticRoutes[6] -> {
            SnackbarHost(hostState = snackbarHostStates[3])
        }
        else -> {}
    }
}