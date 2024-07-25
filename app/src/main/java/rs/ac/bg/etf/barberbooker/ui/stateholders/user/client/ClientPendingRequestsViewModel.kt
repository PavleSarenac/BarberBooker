package rs.ac.bg.etf.barberbooker.ui.stateholders.user.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedReservationWithBarber
import rs.ac.bg.etf.barberbooker.data.room.repositories.ReservationRepository
import javax.inject.Inject

data class ClientPendingRequestsUiState(
    var pendingReservationRequests: List<ExtendedReservationWithBarber> = listOf()
)

@HiltViewModel
class ClientPendingRequestsViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientPendingRequestsUiState())
    val uiState = _uiState

    fun getPendingReservationRequests(clientEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        var pendingReservationRequests = reservationRepository.getClientPendingReservationRequests(clientEmail)

        pendingReservationRequests = pendingReservationRequests.sortedBy { it.startTime }
        pendingReservationRequests = pendingReservationRequests.sortedBy { it.date }

        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(pendingReservationRequests = pendingReservationRequests) }
        }
    }

}