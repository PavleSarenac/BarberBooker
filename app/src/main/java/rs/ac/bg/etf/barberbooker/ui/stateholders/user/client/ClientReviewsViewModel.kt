package rs.ac.bg.etf.barberbooker.ui.stateholders.user.client

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import rs.ac.bg.etf.barberbooker.data.room.repositories.ReservationRepository
import javax.inject.Inject

data class ClientReviewsUiState(
    var newReviewGrade: Int = 0,
    var newReviewText: String = ""
)

@HiltViewModel
class ClientReviewsViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientReviewsUiState())
    val uiState = _uiState

    fun setNewReviewGrade(newReviewGrade: Int) {
        _uiState.update { it.copy(newReviewGrade = newReviewGrade) }
    }

    fun setNewReviewText(newReviewText: String) {
        _uiState.update { it.copy(newReviewText = newReviewText) }
    }

}