package rs.ac.bg.etf.barberbooker.ui.stateholders.user.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.room.repositories.ClientRepository
import javax.inject.Inject

data class ClientProfileUiState(
    var email: String = "",
    var name: String = "",
    var surname: String = "",

    var isNameValid: Boolean = true,
    var isSurnameValid: Boolean = true
)

@HiltViewModel
class ClientProfileViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientProfileUiState())
    val uiState = _uiState

    fun setName(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun setSurname(surname: String) {
        _uiState.update { it.copy(surname = surname) }
    }

    fun fetchClientData(clientEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        val client = clientRepository.getClientByEmail(clientEmail)
        if (client != null) {
            withContext(Dispatchers.Main) {
                _uiState.update { it.copy(
                    email = client.email,
                    name = client.name,
                    surname = client.surname
                ) }
            }
        }
    }

}