package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber.BarberProfileViewModel
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.client.ClientArchiveViewModel

@Composable
fun ClientArchiveScreen(
    clientEmail: String,
    navHostController: NavHostController,
    clientArchiveViewModel: ClientArchiveViewModel = hiltViewModel(),
    barberProfileViewModel: BarberProfileViewModel = hiltViewModel()
) {
    val clientArchiveUiState by clientArchiveViewModel.uiState.collectAsState()
    var isDataFetched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val updateReservationsJob = barberProfileViewModel.updateReservationStatuses()
        updateReservationsJob.join()
        val archiveJob = clientArchiveViewModel.getArchive(clientEmail)
        archiveJob.join()
        isDataFetched = true
    }

    if (!isDataFetched) return

    if (clientArchiveUiState.archive.isEmpty()) {
        Text(
            text = "There are no past appointments.",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(top = 10.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(count = clientArchiveUiState.archive.size) {
                val currentRequest = clientArchiveUiState.archive[it]
                ListItem(
                    headlineContent = {
                        Text(text = currentRequest.barbershopName)
                    },
                    supportingContent = {
                        Text(
                            text = "${currentRequest.date}, ${currentRequest.startTime} - ${currentRequest.endTime}",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    trailingContent = {
                        if (currentRequest.status == "DONE_SUCCESS") {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                tint = Color.Green
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = null,
                                tint = Color.Red
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {
                            navHostController.navigate(
                                "${staticRoutes[20]}/${currentRequest.barberEmail}/${clientEmail}"
                            )
                        }
                )
                Divider()
            }
        }
    }
}