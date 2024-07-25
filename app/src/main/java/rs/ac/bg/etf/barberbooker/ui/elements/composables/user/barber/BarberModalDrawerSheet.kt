package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material.icons.filled.Schedule
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
fun BarberModalDrawerSheet(
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
            selected = currentRoute?.contains("${staticRoutes[8]}/") ?: false,
            onClick = {
                navHostController.navigate("${staticRoutes[8]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary
            )
        )
        NavigationDrawerItem(
            label = { Text(text = "Pending") },
            icon = { Icon(Icons.Filled.HourglassTop, contentDescription = "Pending") },
            selected = currentRoute?.contains("${staticRoutes[9]}/") ?: false,
            onClick = {
                navHostController.navigate("${staticRoutes[9]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary
            )
        )
        NavigationDrawerItem(
            label = { Text(text = "Rejections") },
            icon = { Icon(Icons.Filled.ReportProblem, contentDescription = "Rejected reservation requests") },
            selected = currentRoute?.contains("${staticRoutes[12]}/") ?: false,
            onClick = {
                navHostController.navigate("${staticRoutes[12]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary
            )
        )
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        NavigationDrawerItem(
            label = { Text(text = "Archive") },
            icon = { Icon(Icons.Filled.WorkHistory, contentDescription = "Done haircuts archive") },
            selected = currentRoute?.contains("${staticRoutes[11]}/") ?: false,
            onClick = {
                navHostController.navigate("${staticRoutes[11]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary
            )
        )
        NavigationDrawerItem(
            label = { Text(text = "Reviews") },
            icon = { Icon(Icons.Filled.Reviews, contentDescription = "Reviews that logged in barber has received") },
            selected = currentRoute?.contains("${staticRoutes[10]}/") ?: false,
            onClick = {
                navHostController.navigate("${staticRoutes[10]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary
            )
        )
    }
}
