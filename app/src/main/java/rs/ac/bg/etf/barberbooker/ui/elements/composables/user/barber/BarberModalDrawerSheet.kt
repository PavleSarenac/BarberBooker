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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BarberModalDrawerSheet(drawerState: DrawerState) {
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
