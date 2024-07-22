package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.client

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
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
fun ClientBottomBar(barberEmail: String, navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Schedule, contentDescription = "Appointments") },
            label = { Text(text = "Appointments") },
            selected = currentRoute?.contains("${staticRoutes[7]}/") ?: false,
            onClick = {
                if (currentRoute?.contains("${staticRoutes[7]}/") == true) {
                    return@NavigationBarItem
                }
                navHostController.navigate("${staticRoutes[7]}/${barberEmail}")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            label = { Text(text = "Search") },
            selected = currentRoute?.contains("${staticRoutes[15]}/") ?: false,
            onClick = {
                if (currentRoute?.contains("${staticRoutes[15]}/") == true) {
                    return@NavigationBarItem
                }
                navHostController.navigate("${staticRoutes[15]}/${barberEmail}")
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.WorkHistory, contentDescription = "Archive") },
            label = { Text(text = "Archive") },
            selected = currentRoute?.contains("${staticRoutes[16]}/") ?: false,
            onClick = {
                if (currentRoute?.contains("${staticRoutes[16]}/") == true) {
                    return@NavigationBarItem
                }
                navHostController.navigate("${staticRoutes[16]}/${barberEmail}")
            }
        )
    }
}
