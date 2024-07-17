package rs.ac.bg.etf.barberbooker.ui.stateholders.guest.login

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.room.repositories.ClientRepository
import java.security.MessageDigest
import javax.inject.Inject

data class ClientLoginUiState(
    var email: String = "",
    var password: String = ""
)

@HiltViewModel
class ClientLoginViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientLoginUiState())
    val uiState = _uiState

    fun setEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun setPassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun login(
        snackbarHostState: SnackbarHostState,
        navHostController: NavHostController
    ) = viewModelScope.launch {
        val email = _uiState.value.email
        val password = _uiState.value.password
        val hashedPassword = getSHA256HashedPassword(password)
        if (!clientRepository.areLoginCredentialsValid(email, hashedPassword)) {
            snackbarHostState.showSnackbar("Incorrect credentials!")
            return@launch
        }
        navHostController.navigate("ClientInitialScreen/${email}")
    }

    private fun getSHA256HashedPassword(password: String): String {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val hashBytes = sha256.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

}