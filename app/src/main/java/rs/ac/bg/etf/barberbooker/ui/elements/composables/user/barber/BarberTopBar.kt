package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.barber

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.font.FontWeight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarberTopBar(
    topBarTitle: String,
    drawerState: DrawerState
) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            titleContentColor = MaterialTheme.colorScheme.onSecondary
        ),
        title = {
            Text(
                text = topBarTitle,
                fontWeight = FontWeight.ExtraBold
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    drawerState.apply {
                        if (isClosed) open() else close()
                    }
                }
            } ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Show menu"
                )
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Show account",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    )
}