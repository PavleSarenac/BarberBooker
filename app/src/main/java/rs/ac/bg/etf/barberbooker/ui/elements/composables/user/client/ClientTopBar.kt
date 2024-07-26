package rs.ac.bg.etf.barberbooker.ui.elements.composables.user.client

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.stateholders.BarberBookerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientTopBar(
    topBarTitle: String,
    drawerState: DrawerState,
    navHostController: NavHostController,
    context: Context,
    clientEmail: String,
    barberBookerViewModel: BarberBookerViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var openAccountDialog by rememberSaveable { mutableStateOf(false) }

    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

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
            if (currentRoute != null) {
                if (!currentRoute.contains(staticRoutes[20])) {
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
                } else {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    } ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Go back to previous screen"
                        )
                    }
                }
            }
        },
        actions = {
            IconButton(onClick = {
                openAccountDialog = true
            }) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Show account",
                    tint = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    )

    if (openAccountDialog) {
        AlertDialog(
            onDismissRequest = { openAccountDialog = false },
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(color = MaterialTheme.colorScheme.secondary)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row {
                    IconButton(onClick = { openAccountDialog = false }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Close dialog",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Row {
                    OutlinedButton(
                        onClick = {
                            openAccountDialog = false
                            navHostController.navigate("${staticRoutes[18]}/${clientEmail}")
                        },
                        border = BorderStroke(1.dp, Color.White),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "View profile"
                        )
                    }
                }
                Row {
                    OutlinedButton(
                        onClick = {
                            openAccountDialog = false
                            navHostController.navigate("${staticRoutes[19]}/${clientEmail}")
                        },
                        border = BorderStroke(1.dp, Color.White),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "Edit profile"
                        )
                    }
                }
                Row {
                    OutlinedButton(
                        onClick = {
                            barberBookerViewModel.logOut(context, navHostController)
                        },
                        border = BorderStroke(1.dp, Color.White),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.outlinedButtonColors(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "Log out"
                        )
                    }
                }
            }
        }
    }
}