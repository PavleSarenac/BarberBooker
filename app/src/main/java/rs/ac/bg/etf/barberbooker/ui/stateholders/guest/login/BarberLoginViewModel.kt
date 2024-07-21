package rs.ac.bg.etf.barberbooker.ui.stateholders.guest.login

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.room.repositories.BarberRepository
import java.security.MessageDigest
import javax.inject.Inject

data class BarberLoginUiState(
    var email: String = "",
    var password: String = ""
)

@HiltViewModel
class BarberLoginViewModel @Inject constructor(
    private val barberRepository: BarberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarberLoginUiState())
    val uiState = _uiState

    fun setEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun setPassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun login(
        snackbarHostState: SnackbarHostState,
        navHostController: NavHostController,
        snackbarCoroutineScope: CoroutineScope
    ) = viewModelScope.launch(Dispatchers.Main) {
        val email = _uiState.value.email
        val password = _uiState.value.password
        val hashedPassword = getSHA256HashedPassword(password)

        var areLoginCredentialsValid: Boolean
        withContext(Dispatchers.IO) {
             areLoginCredentialsValid = barberRepository.areLoginCredentialsValid(email, hashedPassword)
        }

        if (!areLoginCredentialsValid) {
            snackbarCoroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Incorrect credentials!",
                    withDismissAction = true
                )
            }
            return@launch
        }
        navHostController.navigate("BarberInitialScreen/${email}")
    }

    private fun getSHA256HashedPassword(password: String): String {
        val sha256 = MessageDigest.getInstance("SHA-256")
        val hashBytes = sha256.digest(password.toByteArray())
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

}