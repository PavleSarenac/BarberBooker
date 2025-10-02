package rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.APPOINTMENTS_CHANNEL_ID
import rs.ac.bg.etf.barberbooker.REJECTIONS_CHANNEL_ID
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedReservationWithClient
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.NotificationData
import rs.ac.bg.etf.barberbooker.data.retrofit.repositories.NotificationRepository
import rs.ac.bg.etf.barberbooker.data.retrofit.repositories.ReservationRepository
import rs.ac.bg.etf.barberbooker.data.*
import java.text.SimpleDateFormat
import javax.inject.Inject

data class BarberPendingRequestsUiState(
    var pendingReservationRequests: List<ExtendedReservationWithClient> = listOf()
)

@HiltViewModel
class BarberPendingRequestsViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarberPendingRequestsUiState())
    val uiState = _uiState

    @SuppressLint("SimpleDateFormat")
    fun getPendingReservationRequests(barberEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        var pendingReservationRequests = reservationRepository.getBarberPendingReservationRequests(barberEmail)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")

        pendingReservationRequests = pendingReservationRequests.sortedBy { it.startTime }
        pendingReservationRequests = pendingReservationRequests.sortedBy { dateFormat.parse(it.date) }

        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(pendingReservationRequests = pendingReservationRequests) }
        }
    }

    fun acceptReservationRequest(
        barberEmail: String,
        reservationId: Int,
        clientEmail: String,
        fcmToken: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        reservationRepository.acceptReservationRequest(reservationId)
        getPendingReservationRequests(barberEmail)
        notificationRepository.sendNotification(
            NotificationData(
                token = fcmToken,
                title = "New notification",
                body = "Your reservation request was accepted",
                route = "${staticRoutes[CLIENT_INITIAL_SCREEN_ROUTE_INDEX]}/${clientEmail}",
                channelId = APPOINTMENTS_CHANNEL_ID
            )
        )
    }

    fun rejectReservationRequest(
        barberEmail: String,
        reservationId: Int,
        clientEmail: String,
        fcmToken: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        reservationRepository.rejectReservationRequest(reservationId)
        getPendingReservationRequests(barberEmail)
        notificationRepository.sendNotification(
            NotificationData(
                token = fcmToken,
                title = "New notification",
                body = "Your reservation request was rejected",
                route = "${staticRoutes[CLIENT_REJECTIONS_SCREEN_ROUTE_INDEX]}/${clientEmail}",
                channelId = REJECTIONS_CHANNEL_ID
            )
        )
    }

}