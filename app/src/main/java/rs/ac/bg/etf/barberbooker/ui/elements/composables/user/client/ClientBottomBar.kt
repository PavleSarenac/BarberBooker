package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.client

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import rs.ac.bg.etf.barberbooker.data.staticRoutes

@Composable
fun ClientBottomBar(clientEmail: String, navHostController: NavHostController) {
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
                navHostController.navigate("${staticRoutes[7]}/${clientEmail}")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            label = { Text(text = "Search") },
            selected = currentRoute?.contains("${staticRoutes[15]}/") ?: false,
            onClick = {
                if (currentRoute?.contains("${staticRoutes[15]}/") == true) {
                    return@NavigationBarItem
                }
                navHostController.navigate("${staticRoutes[15]}/${clientEmail}")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.HourglassTop, contentDescription = "Pending") },
            label = { Text(text = "Pending") },
            selected = currentRoute?.contains("${staticRoutes[21]}/") ?: false,
            onClick = {
                if (currentRoute?.contains("${staticRoutes[21]}/") == true) {
                    return@NavigationBarItem
                }
                navHostController.navigate("${staticRoutes[21]}/${clientEmail}")
            },
            colors = NavigationBarItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary
            )
        )
    }
}
