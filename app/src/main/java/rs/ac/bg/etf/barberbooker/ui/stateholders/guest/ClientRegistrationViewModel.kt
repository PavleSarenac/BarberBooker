package rs.ac.bg.etf.barberbooker.ui.stateholders.guest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.room.entites.Client
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

    fun setPassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun setName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun setSurname(surname: String) {
        _uiState.update { it.copy(surname = surname) }
    }

    fun setPhone(phone: String) {
        _uiState.update { it.copy(phone = phone) }
    }

    fun addNewClient() = viewModelScope.launch {
        val email = _uiState.value.email
        val password = _uiState.value.password
        val name = _uiState.value.name
        val surname = _uiState.value.surname
        val phone = _uiState.value.phone
        val newClient = Client(
            0,
            email,
            password,
            name,
            surname,
            phone
        )
        clientRepository.addNewClient(newClient)
        _uiState.update { ClientRegistrationUiState() }
    }
}