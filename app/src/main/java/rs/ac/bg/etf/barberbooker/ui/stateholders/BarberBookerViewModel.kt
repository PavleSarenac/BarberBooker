package rs.ac.bg.etf.barberbooker.ui.stateholders

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import javax.inject.Inject

data class BarberBookerUiState(
    var startDestination: String = staticRoutes[0],
    var loggedInUserType: String = "",
    var loggedInUserEmail: String = ""
)

@HiltViewModel
class BarberBookerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(BarberBookerUiState())
    val uiState = _uiState

    fun updateLoginData(
        context: Context,
        isLoggedIn: Boolean,
        userEmail: String = "",
        userType: String = ""
    ) = viewModelScope.launch(Dispatchers.IO) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean("is_logged_in", isLoggedIn)
            putString("user_email", userEmail)
            putString("user_type", userType)
            apply()
        }

        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(loggedInUserType = userType, loggedInUserEmail = userEmail) }
        }
    }

    fun updateStartDestination(context: Context) = viewModelScope.launch(Dispatchers.IO) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val userEmail = sharedPreferences.getString("user_email", "")
        val userType = sharedPreferences.getString("user_type", "")

        val startDestination = when {
            isLoggedIn && userType == "client" -> "${staticRoutes[7]}/$userEmail"
            isLoggedIn && userType == "barber" -> "${staticRoutes[8]}/$userEmail"
            else -> staticRoutes[0]
        }

        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(
                startDestination = startDestination,
                loggedInUserType = userType ?: "",
                loggedInUserEmail = userEmail ?: ""
            ) }
        }
    }

}