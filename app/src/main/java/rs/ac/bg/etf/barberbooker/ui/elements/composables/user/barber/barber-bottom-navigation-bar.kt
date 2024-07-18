package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Schedule
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
fun BarberBottomNavigationBar(barberEmail: String, navHostController: NavHostController) {
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
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Account") },
            label = { Text(text = "Account") },
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
    }
}
