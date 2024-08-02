package rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedReservationWithClient
import rs.ac.bg.etf.barberbooker.data.retrofit.repositories.ReservationRepository
import javax.inject.Inject

data class BarberPendingRequestsUiState(
    var pendingReservationRequests: List<ExtendedReservationWithClient> = listOf()
)

@HiltViewModel
class BarberPendingRequestsViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarberPendingRequestsUiState())
    val uiState = _uiState

    fun getPendingReservationRequests(barberEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        var pendingReservationRequests = reservationRepository.getBarberPendingReservationRequests(barberEmail)

        pendingReservationRequests = pendingReservationRequests.sortedBy { it.startTime }
        pendingReservationRequests = pendingReservationRequests.sortedBy { it.date }

        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(pendingReservationRequests = pendingReservationRequests) }
        }
    }

    fun acceptReservationRequest(
        barberEmail: String,
        reservationId: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        reservationRepository.acceptReservationRequest(reservationId)
        getPendingReservationRequests(barberEmail)
    }

    fun rejectReservationRequest(
        barberEmail: String,
        reservationId: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        reservationRepository.rejectReservationRequest(reservationId)
        getPendingReservationRequests(barberEmail)
    }

}