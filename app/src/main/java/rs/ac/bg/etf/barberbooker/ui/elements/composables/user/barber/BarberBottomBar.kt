package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import rs.ac.bg.etf.barberbooker.data.*
import rs.ac.bg.etf.barberbooker.ui.stateholders.BarberBookerViewModel

@Composable
fun BarberBottomBar(
    barberEmail: String,
    navHostController: NavHostController,
    barberBookerViewModel: BarberBookerViewModel
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val uiState by barberBookerViewModel.uiState.collectAsState()

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.HourglassTop, contentDescription = "Pending requests") },
            label = { Text(text = "Pending") },
            selected = currentRoute?.contains("${staticRoutes[BARBER_PENDING_SCREEN_ROUTE_INDEX]}/") ?: false,
            onClick = {
                if (uiState.isEverythingConfirmed) {
                    if (currentRoute?.contains("${staticRoutes[BARBER_PENDING_SCREEN_ROUTE_INDEX]}/") == true) {
                        return@NavigationBarItem
                    }
                    navHostController.navigate("${staticRoutes[BARBER_PENDING_SCREEN_ROUTE_INDEX]}/${barberEmail}")
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Schedule, contentDescription = "Appointments") },
            label = { Text(text = "Appointments") },
            selected = currentRoute?.contains("${staticRoutes[BARBER_INITIAL_SCREEN_ROUTE_INDEX]}/") ?: false,
            onClick = {
                if (uiState.isEverythingConfirmed) {
                    if (currentRoute?.contains("${staticRoutes[BARBER_INITIAL_SCREEN_ROUTE_INDEX]}/") == true) {
                        return@NavigationBarItem
                    }
                    navHostController.navigate("${staticRoutes[BARBER_INITIAL_SCREEN_ROUTE_INDEX]}/${barberEmail}")
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.PendingActions, contentDescription = null) },
            label = { Text(text = "Confirmations") },
            selected = currentRoute?.contains("${staticRoutes[BARBER_CONFIRMATIONS_SCREEN_ROUTE_INDEX]}/") ?: false,
            onClick = {
                if (uiState.isEverythingConfirmed) {
                    if (currentRoute?.contains("${staticRoutes[BARBER_CONFIRMATIONS_SCREEN_ROUTE_INDEX]}/") == true) {
                        return@NavigationBarItem
                    }
                    navHostController.navigate("${staticRoutes[BARBER_CONFIRMATIONS_SCREEN_ROUTE_INDEX]}/${barberEmail}")
                }
            }
        )
    }
}
