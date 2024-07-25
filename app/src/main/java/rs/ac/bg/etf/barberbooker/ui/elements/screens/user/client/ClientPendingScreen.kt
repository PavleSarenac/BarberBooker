package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
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
import androidx.navigation.NavHostController
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.client.ClientPendingRequestsViewModel

@Composable
fun ClientPendingScreen(
    clientEmail: String,
    navHostController: NavHostController,
    clientPendingRequestsViewModel: ClientPendingRequestsViewModel = hiltViewModel()
) {
    val clientPendingRequestsUiState by clientPendingRequestsViewModel.uiState.collectAsState()
    var isDataFetched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val pendingRequestsJob = clientPendingRequestsViewModel.getPendingReservationRequests(clientEmail)
        pendingRequestsJob.join()
        isDataFetched = true
    }

    if (!isDataFetched) return

    LazyColumn(
        contentPadding = PaddingValues(start = 16.dp, top = 10.dp, end = 16.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        items(count = clientPendingRequestsUiState.pendingReservationRequests.size) {
            val currentRequest = clientPendingRequestsUiState.pendingReservationRequests[it]
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
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
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