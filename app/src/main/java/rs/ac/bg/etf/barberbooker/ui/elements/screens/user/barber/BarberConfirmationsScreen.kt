package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import rs.ac.bg.etf.barberbooker.ui.stateholders.BarberBookerViewModel
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber.BarberConfirmationsViewModel
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber.BarberProfileViewModel

@Composable
fun BarberConfirmationsScreen(
    barberEmail: String,
    barberBookerViewModel: BarberBookerViewModel,
    barberConfirmationsViewModel: BarberConfirmationsViewModel = hiltViewModel(),
    barberProfileViewModel: BarberProfileViewModel = hiltViewModel()
) {
    val barberConfirmationsUiState by barberConfirmationsViewModel.uiState.collectAsState()
    var isDataFetched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val updateReservationStatusesJob = barberProfileViewModel.updateReservationStatuses()
        updateReservationStatusesJob.join()
        val confirmationsJob = barberConfirmationsViewModel.getConfirmations(barberEmail)
        confirmationsJob.join()
        isDataFetched = true
    }

    if (!isDataFetched) return

    if (barberConfirmationsUiState.confirmations.isEmpty()) {
        Text(
            text = "No reservations for confirmation.",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )
    } else {
        LazyColumn(
            contentPadding = PaddingValues(top = 10.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(count = barberConfirmationsUiState.confirmations.size) {
                val currentRequest = barberConfirmationsUiState.confirmations[it]
                ListItem(
                    headlineContent = {
                        Text(text = "${currentRequest.clientName} ${currentRequest.clientSurname}")
                    },
                    supportingContent = {
                        Text(
                            text = "${currentRequest.date}, ${currentRequest.startTime} - ${currentRequest.endTime}",
                            color = MaterialTheme.colorScheme.secondary
                        )
                    },
                    trailingContent = {
                        Row {
                            IconButton(
                                onClick = {
                                    barberConfirmationsViewModel.confirmPositiveReservation(
                                        barberEmail,
                                        currentRequest.reservationId,
                                        "DONE_SUCCESS",
                                        barberBookerViewModel
                                    )
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Icon(Icons.Filled.Check, contentDescription = null)
                            }
                            IconButton(
                                onClick = {
                                    barberConfirmationsViewModel.confirmNegativeReservation(
                                        barberEmail,
                                        currentRequest.reservationId,
                                        "DONE_FAILURE",
                                        barberBookerViewModel
                                    )
                                },
                                colors = IconButtonDefaults.iconButtonColors(
                                    contentColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Icon(Icons.Filled.Close, contentDescription = null)
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                )
                Divider()
            }
        }
    }
}