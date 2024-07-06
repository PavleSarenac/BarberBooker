package rs.ac.bg.etf.barberbooker.ui.stateholders.guest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import rs.ac.bg.etf.barberbooker.data.room.repositories.BarberRepository
import javax.inject.Inject

data class BarberRegistrationUiState(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname: String = "",
    var price: Double = 0.0,
    var address: String = "",
    var latitude: Double = 0.0,
    var longitude: Double = 0.0,
    var phone: String = "",
    var workingHours: String = "",
    var workingDays: List<String> = listOf()
)

@HiltViewModel
class BarberRegistrationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val barberRepository: BarberRepository
) : ViewModel() {
    companion object {
        private const val UI_STATE_KEY = "barber_registration_ui_state"
    }

    private val _uiState = MutableStateFlow(BarberRegistrationUiState())
    val uiState = _uiState
}