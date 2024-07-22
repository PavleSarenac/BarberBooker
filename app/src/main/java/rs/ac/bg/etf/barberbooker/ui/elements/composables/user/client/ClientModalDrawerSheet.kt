package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.client

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WorkHistory
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.stateholders.BarberBookerViewModel

@Composable
fun ClientModalDrawerSheet(
    drawerState: DrawerState,
    navHostController: NavHostController,
    barberBookerViewModel: BarberBookerViewModel
) {
    val uiState by barberBookerViewModel.uiState.collectAsState()
    if (uiState.loggedInUserEmail == "") return

    val coroutineScope = rememberCoroutineScope()

    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val screenHeightDp = configuration.screenHeightDp.dp
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerModifier = if (isPortrait) {
        Modifier.width(screenWidthDp * 0.8f)
    } else {
        Modifier.width(screenHeightDp)
    }

    ModalDrawerSheet(
        modifier = drawerModifier
    ) {
        Text(
            text = "BarberBooker",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.padding(16.dp)
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        NavigationDrawerItem(
            label = { Text(text = "Appointments") },
            icon = { Icon(Icons.Filled.Schedule, contentDescription = "Appointments") },
            selected = currentRoute?.contains("${staticRoutes[7]}/") ?: false,
            onClick = {
                navHostController.navigate("${staticRoutes[7]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary
            ),
            badge = { Text(text = "8") }
        )
        NavigationDrawerItem(
            label = { Text(text = "Search") },
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            selected = currentRoute?.contains("${staticRoutes[15]}/") ?: false,
            onClick = {
                navHostController.navigate("${staticRoutes[15]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary

            ),
            badge = { Text(text = "21") }
        )
        NavigationDrawerItem(
            label = { Text(text = "Archive") },
            icon = { Icon(Icons.Filled.WorkHistory, contentDescription = "Past haircuts") },
            selected = currentRoute?.contains("${staticRoutes[16]}/") ?: false,
            onClick = {
                navHostController.navigate("${staticRoutes[16]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary

            ),
            badge = { Text(text = "30") }
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        NavigationDrawerItem(
            label = { Text(text = "Reviews") },
            icon = { Icon(Icons.Filled.Reviews, contentDescription = "All reviews that client has left") },
            selected = currentRoute?.contains("${staticRoutes[17]}/") ?: false,
            onClick = {
                navHostController.navigate("${staticRoutes[17]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary

            ),
            badge = { Text(text = "99+") }
        )
    }
}
