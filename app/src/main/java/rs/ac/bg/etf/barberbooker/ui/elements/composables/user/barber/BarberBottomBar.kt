package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Reviews
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
fun BarberBottomBar(barberEmail: String, navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Schedule, contentDescription = "Appointments") },
            label = { Text(text = "Appointments") },
            selected = currentRoute?.contains("${staticRoutes[8]}/") ?: false,
            onClick = {
                if (currentRoute?.contains("${staticRoutes[8]}/") == true) {
                    return@NavigationBarItem
                }
                navHostController.navigate("${staticRoutes[8]}/${barberEmail}")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.HourglassTop, contentDescription = "Pending requests") },
            label = { Text(text = "Pending") },
            selected = currentRoute?.contains("${staticRoutes[9]}/") ?: false,
            onClick = {
                if (currentRoute?.contains("${staticRoutes[9]}/") == true) {
                    return@NavigationBarItem
                }
                navHostController.navigate("${staticRoutes[9]}/${barberEmail}")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Reviews, contentDescription = "Reviews") },
            label = { Text(text = "Reviews") },
            selected = currentRoute?.contains("${staticRoutes[10]}/") ?: false,
            onClick = {
                if (currentRoute?.contains("${staticRoutes[10]}/") == true) {
                    return@NavigationBarItem
                }
                navHostController.navigate("${staticRoutes[10]}/${barberEmail}")
            }
        )
    }
}
