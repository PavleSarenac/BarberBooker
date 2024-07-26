package rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedReservationWithClient
import rs.ac.bg.etf.barberbooker.data.room.repositories.ReservationRepository
import javax.inject.Inject

data class BarberArchiveUiState(
    var archive: List<ExtendedReservationWithClient> = listOf()
)

@HiltViewModel
class BarberArchiveViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarberArchiveUiState())
    val uiState = _uiState

    fun getArchive(barberEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        var archive = reservationRepository.getBarberArchive(barberEmail)

        archive = archive.sortedBy { it.startTime }
        archive = archive.sortedBy { it.date }

        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(archive = archive) }
        }
    }

}