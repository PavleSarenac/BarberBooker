package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Cancel
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
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.stateholders.BarberBookerUiState
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

    var selectedItem by rememberSaveable { mutableStateOf("Appointments") }

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
            selected = selectedItem == "Appointments",
            onClick = {
                selectedItem = "Appointments"
                navHostController.navigate("${staticRoutes[8]}/${uiState.loggedInUserEmail}")
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
            label = { Text(text = "Pending") },
            icon = { Icon(Icons.Filled.HourglassTop, contentDescription = "Pending") },
            selected = selectedItem == "Pending",
            onClick = {
                selectedItem = "Pending"
                navHostController.navigate("${staticRoutes[9]}/${uiState.loggedInUserEmail}")
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
            label = { Text(text = "Revenue") },
            icon = { Icon(Icons.Filled.AttachMoney, contentDescription = "Revenue") },
            selected = selectedItem == "Revenue",
            onClick = {
                selectedItem = "Revenue"
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
        Divider(modifier = Modifier.padding(vertical = 8.dp))
        NavigationDrawerItem(
            label = { Text(text = "Reviews") },
            icon = { Icon(Icons.Filled.Reviews, contentDescription = "Reviews that logged in barber has received") },
            selected = selectedItem == "Reviews",
            onClick = {
                selectedItem = "Reviews"
                navHostController.navigate("${staticRoutes[11]}/${uiState.loggedInUserEmail}")
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
        NavigationDrawerItem(
            label = { Text(text = "Archive") },
            icon = { Icon(Icons.Filled.WorkHistory, contentDescription = "Done haircuts archive") },
            selected = selectedItem == "Archive",
            onClick = {
                selectedItem = "Archive"
                navHostController.navigate("${staticRoutes[12]}/${uiState.loggedInUserEmail}")
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
        NavigationDrawerItem(
            label = { Text(text = "Rejections") },
            icon = { Icon(Icons.Filled.ReportProblem, contentDescription = "Rejected reservation requests") },
            selected = selectedItem == "Rejections",
            onClick = {
                selectedItem = "Rejections"
                navHostController.navigate("${staticRoutes[13]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary

            ),
            badge = { Text(text = "15") }
        )
        NavigationDrawerItem(
            label = { Text(text = "Cancellations") },
            icon = { Icon(Icons.Filled.Cancel, contentDescription = "Cancelled reservation requests") },
            selected = selectedItem == "Cancellations",
            onClick = {
                selectedItem = "Cancellations"
                navHostController.navigate("${staticRoutes[14]}/${uiState.loggedInUserEmail}")
                coroutineScope.launch {
                    drawerState.close()
                }
            },
            colors = NavigationDrawerItemDefaults.colors(
                selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                unselectedTextColor = MaterialTheme.colorScheme.onPrimary,
                selectedContainerColor = MaterialTheme.colorScheme.secondary

            ),
            badge = { Text(text = "3") }
        )
    }
}
