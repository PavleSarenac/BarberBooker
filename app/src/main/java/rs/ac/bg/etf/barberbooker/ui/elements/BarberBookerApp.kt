package rs.ac.bg.etf.barberbooker.ui.elements

import android.app.Activity
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.elements.composables.guest.GuestTopBar
import rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber.BarberBottomBar
import rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber.BarberModalDrawerSheet
import rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber.BarberTopBar
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.InitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInAsBarberScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInAsClientScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpAsBarberScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpAsClientScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberInitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberPendingScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberRevenueScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientInitialScreen
import rs.ac.bg.etf.barberbooker.ui.stateholders.BarberBookerUiState
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

    val snackbarHostState = remember { SnackbarHostState() }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        barberBookerViewModel.updateStartDestination(context)
    }

    if (uiState.isInitialScreenLoading) {
        Column(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .fillMaxSize()
        ) {}
        return
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                if (uiState.loggedInUserType == "client") {
                    /* TODO */
                }
                else if (uiState.loggedInUserType == "barber") {
                    BarberModalDrawerSheet(drawerState)
                }
            }
        }
    ) {
        BarberBookerScaffold(
            navHostController,
            currentRoute,
            context,
            barberBookerActivity,
            uiState,
            snackbarHostState,
            barberBookerViewModel,
            drawerState,
            coroutineScope
        )
    }

}

@Composable
fun BarberBookerScaffold(
    navHostController: NavHostController,
    currentRoute: String,
    context: Context,
    barberBookerActivity: Activity?,
    uiState: BarberBookerUiState,
    snackbarHostState: SnackbarHostState,
    barberBookerViewModel: BarberBookerViewModel,
    drawerState: DrawerState,
    coroutineScope: CoroutineScope
) {
    Scaffold(
        topBar = {
            ScaffoldTopBar(currentRoute, navHostController, drawerState)
        },
        bottomBar = {
            ScaffoldBottomBar(currentRoute, navHostController)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
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
                SignUpAsClientScreen(navHostController, paddingValues, snackbarHostState)
            }
            composable(route = staticRoutes[4]) {
                SignUpAsBarberScreen(navHostController, paddingValues, snackbarHostState)
            }
            composable(route = staticRoutes[5]) {
                LogInAsClientScreen(navHostController, paddingValues, snackbarHostState)
            }
            composable(route = staticRoutes[6]) {
                LogInAsBarberScreen(navHostController, paddingValues, snackbarHostState)
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
                    if (drawerState.isOpen) {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    } else {
                        barberBookerActivity?.finish()
                    }
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
                LoggedInUserRegularScreenBackHandler(drawerState, navHostController)
                BarberPendingScreen(barberEmail, navHostController)
            }
            composable(
                route = "${staticRoutes[10]}/{barberEmail}",
                arguments = listOf(
                    navArgument("barberEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val barberEmail = navBackStackEntry.arguments?.getString("barberEmail") ?: ""
                LoggedInUserRegularScreenBackHandler(drawerState, navHostController)
                BarberRevenueScreen(barberEmail, navHostController)
            }
        }
    }
}

@Composable
fun ScaffoldTopBar(
    currentRoute: String,
    navHostController: NavHostController,
    drawerState: DrawerState
) {
    when {
        currentRoute == staticRoutes[1] -> GuestTopBar(
            topBarTitle = "Log in to BarberBooker",
            navHostController = navHostController
        )
        currentRoute == staticRoutes[2] -> GuestTopBar(
            topBarTitle = "Sign up for BarberBooker",
            navHostController = navHostController
        )
        currentRoute == staticRoutes[3] -> GuestTopBar(
            topBarTitle = "Sign up as a client",
            navHostController = navHostController
        )
        currentRoute == staticRoutes[4] -> GuestTopBar(
            topBarTitle = "Sign up as a barber",
            navHostController = navHostController
        )
        currentRoute == staticRoutes[5] -> GuestTopBar(
            topBarTitle = "Log in as a client",
            navHostController = navHostController
        )
        currentRoute == staticRoutes[6] -> GuestTopBar(
            topBarTitle = "Log in as a barber",
            navHostController = navHostController
        )
        currentRoute.contains(staticRoutes[8]) -> BarberTopBar(
            topBarTitle = "Appointments",
            drawerState = drawerState
        )
        currentRoute.contains(staticRoutes[9]) -> BarberTopBar(
            topBarTitle = "Pending requests",
            drawerState = drawerState
        )
        currentRoute.contains(staticRoutes[10]) -> BarberTopBar(
            topBarTitle = "Revenue",
            drawerState = drawerState
        )
        else -> {}
    }
}

@Composable
fun ScaffoldBottomBar(currentRoute: String, navHostController: NavHostController) {
    if (currentRoute.contains("Barber") && currentRoute.contains("/")) {
        val barberEmail = currentRoute.substring(currentRoute.indexOf("/") + 1)
        BarberBottomBar(barberEmail, navHostController)
    }
}

@Composable
fun LoggedInUserRegularScreenBackHandler(
    drawerState: DrawerState,
    navHostController: NavHostController
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        if (drawerState.isOpen) {
            coroutineScope.launch {
                drawerState.close()
            }
        } else {
            navHostController.popBackStack()
        }
    }
}