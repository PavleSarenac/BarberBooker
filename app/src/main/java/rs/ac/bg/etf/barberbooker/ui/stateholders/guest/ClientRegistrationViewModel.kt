package rs.ac.bg.etf.barberbooker.ui.stateholders.guest

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val savedStateHandle: SavedStateHandle,
    private val clientRepository: ClientRepository
) : ViewModel() {
    companion object {
        private const val UI_STATE_KEY = "client_registration_ui_state"
    }

    private val _uiState = savedStateHandle.getStateFlow(UI_STATE_KEY, ClientRegistrationUiState())
}