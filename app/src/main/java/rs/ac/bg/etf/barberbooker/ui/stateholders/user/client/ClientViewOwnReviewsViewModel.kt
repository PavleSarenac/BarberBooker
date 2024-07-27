package rs.ac.bg.etf.barberbooker.ui.stateholders.user.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedReviewWithBarber
import rs.ac.bg.etf.barberbooker.data.room.repositories.ReviewRepository
import javax.inject.Inject

data class ClientViewOwnReviewsUiState(
    var clientReviews: List<ExtendedReviewWithBarber> = listOf()
)

@HiltViewModel
class ClientViewOwnReviewsViewModel @Inject constructor(
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientViewOwnReviewsUiState())
    val uiState = _uiState

    fun getClientReviews(clientEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        var clientReviews = reviewRepository.getClientReviews(clientEmail)
        clientReviews = clientReviews.sortedByDescending { it.date }
        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(clientReviews = clientReviews) }
        }
    }

}