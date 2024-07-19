package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import rs.ac.bg.etf.barberbooker.data.staticRoutes

@Composable
fun BarberBottomBar(barberEmail: String, navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Schedule, contentDescription = "Appointments") },
            label = { Text(text = "Appointments") },
            selected = currentRoute == "${staticRoutes[8]}/${barberEmail}",
            onClick = {
                if (currentRoute != "${staticRoutes[8]}/${barberEmail}") {
                    navHostController.navigate("${staticRoutes[8]}/${barberEmail}") {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo("${staticRoutes[8]}/${barberEmail}") {
                            saveState = true
                            inclusive = false
                        }
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.HourglassTop, contentDescription = "Pending requests") },
            label = { Text(text = "Pending") },
            selected = currentRoute == "${staticRoutes[9]}/${barberEmail}",
            onClick = {
                if (currentRoute != "${staticRoutes[9]}/${barberEmail}") {
                    navHostController.navigate("${staticRoutes[9]}/${barberEmail}") {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo("${staticRoutes[8]}/${barberEmail}") {
                            saveState = true
                            inclusive = false
                        }
                    }
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.AttachMoney, contentDescription = "Revenue") },
            label = { Text(text = "Revenue") },
            selected = currentRoute == "${staticRoutes[10]}/${barberEmail}",
            onClick = {
                if (currentRoute != "${staticRoutes[10]}/${barberEmail}") {
                    navHostController.navigate("${staticRoutes[10]}/${barberEmail}") {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo("${staticRoutes[8]}/${barberEmail}") {
                            saveState = true
                            inclusive = false
                        }
                    }
                }
            }
        )
    }
}
