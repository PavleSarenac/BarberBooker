package rs.ac.bg.etf.barberbooker.ui.stateholders.guest

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import rs.ac.bg.etf.barberbooker.data.room.repositories.ClientRepository
import javax.inject.Inject

data class ClientRegistrationUiState(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname: String = "",
    var phone: String = ""
)

@HiltViewModel
class ClientRegistrationViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ViewModel() {
    companion object {
        private const val UI_STATE_KEY = "client_registration_ui_state"
    }

    private val _uiState = MutableStateFlow(ClientRegistrationUiState())
    val uiState = _uiState

    fun setEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }
}