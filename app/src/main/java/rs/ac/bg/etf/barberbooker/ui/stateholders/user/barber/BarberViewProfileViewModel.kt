package rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.room.repositories.BarberRepository
import java.text.DecimalFormat
import javax.inject.Inject

data class BarberViewProfileUiState(
    var email: String = "",
    var barbershopName: String = "",
    var price: String = "",
    var phone: String = "",
    var country: String = "",
    var city: String = "",
    var municipality: String = "",
    var address: String = "",
    var workingDays: String = "",
    var workingHours: String = ""
)

@HiltViewModel
class BarberViewProfileViewModel @Inject constructor(
    private val barberRepository: BarberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarberViewProfileUiState())
    val uiState = _uiState

    private val decimalFormat = DecimalFormat("#.00")

    fun fetchBarberData(barberEmail: String) = viewModelScope.launch(Dispatchers.IO) {
        val barber = barberRepository.getBarberByEmail(barberEmail)
        if (barber != null) {
            withContext(Dispatchers.Main) {
                _uiState.update { it.copy(
                    email = barber.email,
                    barbershopName = barber.barbershopName,
                    price = decimalFormat.format(barber.price),
                    phone = barber.phone,
                    country = barber.country,
                    city = barber.city,
                    municipality = barber.municipality,
                    address = barber.address,
                    workingDays = barber.workingDays,
                    workingHours = barber.workingHours
                ) }
            }
        }
    }

}