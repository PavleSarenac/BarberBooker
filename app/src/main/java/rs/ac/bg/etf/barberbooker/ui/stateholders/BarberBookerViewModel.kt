package rs.ac.bg.etf.barberbooker.ui.stateholders

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedReservationWithClient
import rs.ac.bg.etf.barberbooker.data.retrofit.repositories.ReservationRepository
import rs.ac.bg.etf.barberbooker.data.*
import java.util.Calendar
import javax.inject.Inject

data class BarberBookerUiState(
    var startDestination: String = staticRoutes[INITIAL_SCREEN_ROUTE_INDEX],
    var loggedInUserType: String = "",
    var loggedInUserEmail: String = "",
    var confirmations: List<ExtendedReservationWithClient> = listOf(),
    var isEverythingConfirmed: Boolean = false
)

@HiltViewModel
class BarberBookerViewModel @Inject constructor(
    private val reservationRepository: ReservationRepository
) : ViewModel() {

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

    fun updateStartDestination(
        context: Context,
        notificationRoute: String
    ) = viewModelScope.launch(Dispatchers.IO) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val userEmail = sharedPreferences.getString("user_email", "")
        val userType = sharedPreferences.getString("user_type", "")

        val startDestination = when {
            isLoggedIn && userType == "client" -> {
                if (notificationRoute == "") {
                    "${staticRoutes[CLIENT_INITIAL_SCREEN_ROUTE_INDEX]}/$userEmail"
                } else {
                    notificationRoute
                }
            }
            isLoggedIn && userType == "barber" -> {
                val job = getConfirmations(userEmail!!)
                job.join()
                if (_uiState.value.isEverythingConfirmed) {
                    if (notificationRoute == "") {
                        "${staticRoutes[BARBER_PENDING_SCREEN_ROUTE_INDEX]}/$userEmail"
                    } else {
                        notificationRoute
                    }
                } else {
                    "${staticRoutes[BARBER_CONFIRMATIONS_SCREEN_ROUTE_INDEX]}/$userEmail"
                }
            }
            else -> staticRoutes[INITIAL_SCREEN_ROUTE_INDEX]
        }

        withContext(Dispatchers.Main) {
            _uiState.update {
                it.copy(
                    startDestination = startDestination,
                    loggedInUserType = userType ?: "",
                    loggedInUserEmail = userEmail ?: ""
                )
            }
        }
    }

    fun getConfirmations(barberEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        val confirmations = reservationRepository.getBarberConfirmations(barberEmail)
        val isEverythingConfirmed = !confirmations.any {
            getDateTimeInMillis(it.date, it.endTime) < getCurrentDateTimeMidnightMillis()
        }
        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(
                confirmations = confirmations,
                isEverythingConfirmed = isEverythingConfirmed
            ) }
        }
    }

    private fun getCurrentDateTimeMidnightMillis(): Long {
        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }

    private fun getDateTimeInMillis(date: String, time: String): Long {
        val calendar = Calendar.getInstance()

        val day = date.substring(0, 2).toInt()
        val month = date.substring(3, 5).toInt() - 1
        val year = date.substring(6).toInt()

        val hour = time.split(":")[0].toInt()
        val minute = time.split(":")[1].toInt()

        calendar.set(year, month, day, hour, minute, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.timeInMillis
    }

    fun logOut(context: Context, navHostController: NavHostController) = viewModelScope.launch(Dispatchers.Main) {
        withContext(Dispatchers.IO) {
            Firebase.messaging.deleteToken()

            val sharedPreferences: SharedPreferences = context.getSharedPreferences("login_data", Context.MODE_PRIVATE)
            with(sharedPreferences.edit()) {
                putBoolean("is_logged_in", false)
                putString("user_email", "")
                putString("user_type", "")
                apply()
            }
        }

        _uiState.update {
            it.copy(
                loggedInUserType = "",
                loggedInUserEmail = ""
            )
        }

        navHostController.navigate(staticRoutes[INITIAL_SCREEN_ROUTE_INDEX])
    }

}