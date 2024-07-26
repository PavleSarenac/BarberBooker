package rs.ac.bg.etf.barberbooker.ui.stateholders.user.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedReviewWithClient
import rs.ac.bg.etf.barberbooker.data.room.repositories.ReviewRepository
import javax.inject.Inject

data class ClientViewBarberReviewsUiState(
    var barberReviews: List<ExtendedReviewWithClient> = listOf()
)

@HiltViewModel
class ClientViewBarberReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientViewBarberReviewsUiState())
    val uiState = _uiState

    fun getBarberReviews(barberEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        var barberReviews = reviewRepository.getBarberReviews(barberEmail)
        barberReviews = barberReviews.sortedByDescending { it.date }
        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(barberReviews = barberReviews) }
        }
    }

}