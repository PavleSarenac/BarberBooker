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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import rs.ac.bg.etf.barberbooker.ui.elements.composables.user.client.ClientBottomBar
import rs.ac.bg.etf.barberbooker.ui.elements.composables.user.client.ClientModalDrawerSheet
import rs.ac.bg.etf.barberbooker.ui.elements.composables.user.client.ClientTopBar
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.InitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInAsBarberScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInAsClientScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.login.LogInScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpAsBarberScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpAsClientScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration.SignUpScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberArchiveScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberEditProfileScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberInitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberPendingScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberRejectionsScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberReviewsScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber.BarberViewProfileScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientArchiveScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientEditProfileScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientInitialScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientPendingScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientRejectionsScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientReviewsScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientSearchBarbersScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientViewBarberProfileScreen
import rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client.ClientViewProfileScreen
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

    var isStartDestinationLoaded by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val job = barberBookerViewModel.updateStartDestination(context)
        job.join()
        isStartDestinationLoaded = true
    }

    if (!isStartDestinationLoaded) {
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
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                if (uiState.loggedInUserType == "client") {
                    ClientModalDrawerSheet(drawerState, navHostController, barberBookerViewModel)
                }
                else if (uiState.loggedInUserType == "barber") {
                    BarberModalDrawerSheet(drawerState, navHostController, barberBookerViewModel)
                }
            }
        },
        gesturesEnabled = uiState.loggedInUserEmail != ""
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
            ScaffoldTopBar(currentRoute, navHostController, drawerState, context, uiState, barberBookerViewModel)
        },
        bottomBar = {
            ScaffoldBottomBar(navHostController, uiState)
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = uiState.startDestination,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = staticRoutes[0]) {
                BackHandler {
                    barberBookerActivity?.finish()
                }
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
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: uiState.loggedInUserEmail
                BackHandler {
                    barberBookerActivity?.finish()
                }
                val previousRoute = navHostController.previousBackStackEntry?.destination?.route
                if (previousRoute == staticRoutes[5]) {
                    barberBookerViewModel.updateLoginData(context, true, clientEmail, "client")
                }
                if (uiState.loggedInUserEmail != "") {
                    ClientInitialScreen(clientEmail, navHostController)
                }
            }
            composable(
                route = "${staticRoutes[8]}/{barberEmail}",
                arguments = listOf(
                    navArgument("barberEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val barberEmail = navBackStackEntry.arguments?.getString("barberEmail") ?: uiState.loggedInUserEmail
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
                if (uiState.loggedInUserEmail != "") {
                    BarberInitialScreen(barberEmail)
                }
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
                LoggedInBarberRegularScreenBackHandler(drawerState, navHostController, barberEmail)
                if (uiState.loggedInUserEmail != "") {
                    BarberPendingScreen(barberEmail)
                }
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
                LoggedInBarberRegularScreenBackHandler(drawerState, navHostController, barberEmail)
                if (uiState.loggedInUserEmail != "") {
                    BarberReviewsScreen(barberEmail, navHostController)
                }
            }
            composable(
                route = "${staticRoutes[11]}/{barberEmail}",
                arguments = listOf(
                    navArgument("barberEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val barberEmail = navBackStackEntry.arguments?.getString("barberEmail") ?: ""
                LoggedInBarberRegularScreenBackHandler(drawerState, navHostController, barberEmail)
                if (uiState.loggedInUserEmail != "") {
                    BarberArchiveScreen(barberEmail)
                }
            }
            composable(
                route = "${staticRoutes[12]}/{barberEmail}",
                arguments = listOf(
                    navArgument("barberEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val barberEmail = navBackStackEntry.arguments?.getString("barberEmail") ?: ""
                LoggedInBarberRegularScreenBackHandler(drawerState, navHostController, barberEmail)
                if (uiState.loggedInUserEmail != "") {
                    BarberRejectionsScreen(barberEmail)
                }
            }
            composable(
                route = "${staticRoutes[13]}/{barberEmail}",
                arguments = listOf(
                    navArgument("barberEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val barberEmail = navBackStackEntry.arguments?.getString("barberEmail") ?: ""
                LoggedInBarberRegularScreenBackHandler(drawerState, navHostController, barberEmail)
                if (uiState.loggedInUserEmail != "") {
                    BarberViewProfileScreen(barberEmail)
                }
            }
            composable(
                route = "${staticRoutes[14]}/{barberEmail}",
                arguments = listOf(
                    navArgument("barberEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val barberEmail = navBackStackEntry.arguments?.getString("barberEmail") ?: ""
                LoggedInBarberRegularScreenBackHandler(drawerState, navHostController, barberEmail)
                if (uiState.loggedInUserEmail != "") {
                    BarberEditProfileScreen(barberEmail, snackbarHostState)
                }
            }
            composable(
                route = "${staticRoutes[15]}/{clientEmail}",
                arguments = listOf(
                    navArgument("clientEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: ""
                LoggedInClientRegularScreenBackHandler(drawerState, navHostController, clientEmail)
                if (uiState.loggedInUserEmail != "") {
                    ClientSearchBarbersScreen(clientEmail, navHostController)
                }
            }
            composable(
                route = "${staticRoutes[16]}/{clientEmail}",
                arguments = listOf(
                    navArgument("clientEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: ""
                LoggedInClientRegularScreenBackHandler(drawerState, navHostController, clientEmail)
                if (uiState.loggedInUserEmail != "") {
                    ClientArchiveScreen(clientEmail, navHostController)
                }
            }
            composable(
                route = "${staticRoutes[17]}/{clientEmail}",
                arguments = listOf(
                    navArgument("clientEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: ""
                LoggedInClientRegularScreenBackHandler(drawerState, navHostController, clientEmail)
                if (uiState.loggedInUserEmail != "") {
                    ClientReviewsScreen(clientEmail)
                }
            }
            composable(
                route = "${staticRoutes[18]}/{clientEmail}",
                arguments = listOf(
                    navArgument("clientEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: ""
                LoggedInClientRegularScreenBackHandler(drawerState, navHostController, clientEmail)
                if (uiState.loggedInUserEmail != "") {
                    ClientViewProfileScreen(clientEmail)
                }
            }
            composable(
                route = "${staticRoutes[19]}/{clientEmail}",
                arguments = listOf(
                    navArgument("clientEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: ""
                LoggedInClientRegularScreenBackHandler(drawerState, navHostController, clientEmail)
                if (uiState.loggedInUserEmail != "") {
                    ClientEditProfileScreen(clientEmail, snackbarHostState)
                }
            }
            composable(
                route = "${staticRoutes[20]}/{barberEmail}/{clientEmail}",
                arguments = listOf(
                    navArgument("barberEmail") {
                        type = NavType.StringType
                    },
                    navArgument("clientEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val barberEmail = navBackStackEntry.arguments?.getString("barberEmail") ?: ""
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: ""
                BackHandler {
                    if (drawerState.isOpen) {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    } else {
                        navHostController.popBackStack()
                    }
                }
                if (uiState.loggedInUserEmail != "") {
                    ClientViewBarberProfileScreen(barberEmail, clientEmail, snackbarHostState)
                }
            }
            composable(
                route = "${staticRoutes[21]}/{clientEmail}",
                arguments = listOf(
                    navArgument("clientEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: ""
                LoggedInClientRegularScreenBackHandler(drawerState, navHostController, clientEmail)
                if (uiState.loggedInUserEmail != "") {
                    ClientPendingScreen(
                        clientEmail = clientEmail,
                        navHostController = navHostController
                    )
                }
            }
            composable(
                route = "${staticRoutes[22]}/{clientEmail}",
                arguments = listOf(
                    navArgument("clientEmail") {
                        type = NavType.StringType
                    }
                )
            ) {navBackStackEntry ->
                val clientEmail = navBackStackEntry.arguments?.getString("clientEmail") ?: ""
                LoggedInClientRegularScreenBackHandler(drawerState, navHostController, clientEmail)
                if (uiState.loggedInUserEmail != "") {
                    ClientRejectionsScreen(
                        clientEmail = clientEmail,
                        navHostController = navHostController
                    )
                }
            }
        }
    }
}

@Composable
fun ScaffoldTopBar(
    currentRoute: String,
    navHostController: NavHostController,
    drawerState: DrawerState,
    context: Context,
    uiState: BarberBookerUiState,
    barberBookerViewModel: BarberBookerViewModel
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
        currentRoute.contains(staticRoutes[7]) && uiState.loggedInUserEmail != "" -> ClientTopBar(
            topBarTitle = "Appointments",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            clientEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[8]) && uiState.loggedInUserEmail != "" -> BarberTopBar(
            topBarTitle = "Appointments",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            barberEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[9]) && uiState.loggedInUserEmail != "" -> BarberTopBar(
            topBarTitle = "Pending requests",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            barberEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[10]) && uiState.loggedInUserEmail != "" -> BarberTopBar(
            topBarTitle = "Reviews",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            barberEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[11]) && uiState.loggedInUserEmail != "" -> BarberTopBar(
            topBarTitle = "Archive",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            barberEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[12]) && uiState.loggedInUserEmail != "" -> BarberTopBar(
            topBarTitle = "Rejections",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            barberEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[13]) && uiState.loggedInUserEmail != "" -> BarberTopBar(
            topBarTitle = "My profile",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            barberEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[14]) && uiState.loggedInUserEmail != "" -> BarberTopBar(
            topBarTitle = "Edit Profile",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            barberEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[15]) && uiState.loggedInUserEmail != "" -> ClientTopBar(
            topBarTitle = "Search",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            clientEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[16]) && uiState.loggedInUserEmail != "" -> ClientTopBar(
            topBarTitle = "Archive",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            clientEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[17]) && uiState.loggedInUserEmail != "" -> ClientTopBar(
            topBarTitle = "My reviews",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            clientEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[18]) && uiState.loggedInUserEmail != "" -> ClientTopBar(
            topBarTitle = "My profile",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            clientEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[19]) && uiState.loggedInUserEmail != "" -> ClientTopBar(
            topBarTitle = "Edit profile",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            clientEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[20]) && uiState.loggedInUserEmail != "" -> ClientTopBar(
            topBarTitle = "Barbershop",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            clientEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[21]) && uiState.loggedInUserEmail != "" -> ClientTopBar(
            topBarTitle = "Pending requests",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            clientEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        currentRoute.contains(staticRoutes[22]) && uiState.loggedInUserEmail != "" -> ClientTopBar(
            topBarTitle = "Rejections",
            drawerState = drawerState,
            navHostController = navHostController,
            context = context,
            clientEmail = uiState.loggedInUserEmail,
            barberBookerViewModel = barberBookerViewModel
        )
        else -> {}
    }
}

@Composable
fun ScaffoldBottomBar(
    navHostController: NavHostController,
    uiState: BarberBookerUiState
) {
    if (uiState.loggedInUserEmail != "" && uiState.loggedInUserType == "barber") {
        BarberBottomBar(uiState.loggedInUserEmail, navHostController)
    }
    if (uiState.loggedInUserEmail != "" && uiState.loggedInUserType == "client") {
        ClientBottomBar(uiState.loggedInUserEmail, navHostController)
    }
}

@Composable
fun LoggedInBarberRegularScreenBackHandler(
    drawerState: DrawerState,
    navHostController: NavHostController,
    barberEmail: String
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        if (drawerState.isOpen) {
            coroutineScope.launch {
                drawerState.close()
            }
        } else {
            navHostController.navigate("${staticRoutes[8]}/${barberEmail}")
        }
    }
}

@Composable
fun LoggedInClientRegularScreenBackHandler(
    drawerState: DrawerState,
    navHostController: NavHostController,
    clientEmail: String
) {
    val coroutineScope = rememberCoroutineScope()
    BackHandler {
        if (drawerState.isOpen) {
            coroutineScope.launch {
                drawerState.close()
            }
        } else {
            navHostController.navigate("${staticRoutes[7]}/${clientEmail}")
        }
    }
}