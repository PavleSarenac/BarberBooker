package rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.daysOfTheWeek
import rs.ac.bg.etf.barberbooker.data.room.repositories.BarberRepository
import java.text.DecimalFormat
import javax.inject.Inject

data class BarberProfileUiState(
    var email: String = "",
    var barbershopName: String = "",
    var price: String = "",
    var phone: String = "",
    var country: String = "",
    var city: String = "",
    var municipality: String = "",
    var address: String = "",
    var workingDays: String = "",
    var workingHours: String = "",

    var isBarbershopNameValid: Boolean = true,
    var isPhoneValid: Boolean = true,
    var isPriceValid: Boolean = true,
    var isCountryValid: Boolean = true,
    var isCityValid: Boolean = true,
    var isMunicipalityValid: Boolean = true,
    var isAddressValid: Boolean = true
)

@HiltViewModel
class BarberProfileViewModel @Inject constructor(
    private val barberRepository: BarberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarberProfileUiState())
    val uiState = _uiState

    private val decimalFormat = DecimalFormat("#.00")

    fun setBarbershopName(barbershopName: String) {
        _uiState.update { it.copy(barbershopName = barbershopName) }
    }

    fun setPhone(phone: String) {
        _uiState.update { it.copy(phone = phone) }
    }

    fun setPrice(price: String) {
        _uiState.update { it.copy(price = price) }
    }

    fun setCountry(country: String) {
        _uiState.update { it.copy(country = country) }
    }

    fun setCity(city: String) {
        _uiState.update { it.copy(city = city) }
    }

    fun setMunicipality(municipality: String) {
        _uiState.update { it.copy(municipality = municipality) }
    }

    fun setAddress(address: String) {
        _uiState.update { it.copy(address = address) }
    }

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

    fun updateProfile(
        snackbarHostState: SnackbarHostState,
        workingDayStartTime: String,
        workingDayEndTime: String,
        isMondayChecked: Boolean,
        isTuesdayChecked: Boolean,
        isWednesdayChecked: Boolean,
        isThursdayChecked: Boolean,
        isFridayChecked: Boolean,
        isSaturdayChecked: Boolean,
        isSundayChecked: Boolean,
        snackbarCoroutineScope: CoroutineScope
    ) = viewModelScope.launch(Dispatchers.Main) {
        val email = _uiState.value.email
        val barbershopName = _uiState.value.barbershopName
        val phone = _uiState.value.phone
        val price = _uiState.value.price
        val country = _uiState.value.country
        val city = _uiState.value.city
        val municipality = _uiState.value.municipality
        val address = _uiState.value.address
        val selectedWorkingDays = getSelectedWorkingDays(
            isMondayChecked,
            isTuesdayChecked,
            isWednesdayChecked,
            isThursdayChecked,
            isFridayChecked,
            isSaturdayChecked,
            isSundayChecked,
        )

        if (!isDataValid(
                barbershopName,
                phone,
                price,
                country,
                city,
                municipality,
                address,
                workingDayStartTime,
                workingDayEndTime,
                selectedWorkingDays
            )) {
            snackbarCoroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Invalid data format!",
                    withDismissAction = true
                )
            }
            return@launch
        }

        withContext(Dispatchers.IO) {
            barberRepository.updateBarberProfile(
                email,
                barbershopName,
                price.toDouble(),
                phone,
                country,
                city,
                municipality,
                address,
                selectedWorkingDays,
                "$workingDayStartTime - $workingDayEndTime"
            )
        }

        snackbarCoroutineScope.launch {
            snackbarHostState.showSnackbar(
                message = "Profile updated!",
                withDismissAction = true
            )
        }
    }

    private fun isDataValid(
        barbershopName: String,
        phone: String,
        price: String,
        country: String,
        city: String,
        municipality: String,
        address: String,
        workingDayStartTime: String,
        workingDayEndTime: String,
        selectedWorkingDays: String
    ): Boolean {
        var isDataValid = true
        if (!isBarbershopNameValid(barbershopName)) isDataValid = false
        if (!isPhoneValid(phone)) isDataValid = false
        if (!isPriceValid(price)) isDataValid = false
        if (!isCountryValid(country)) isDataValid = false
        if (!isCityValid(city)) isDataValid = false
        if (!isMunicipalityValid(municipality)) isDataValid = false
        if (!isAddressValid(address)) isDataValid = false
        if (!areWorkingHoursValid(workingDayStartTime, workingDayEndTime)) isDataValid = false
        if (!areSelectedWorkingDaysValid(selectedWorkingDays)) isDataValid = false
        return isDataValid
    }

    private fun getSelectedWorkingDays(
        isMondayChecked: Boolean,
        isTuesdayChecked: Boolean,
        isWednesdayChecked: Boolean,
        isThursdayChecked: Boolean,
        isFridayChecked: Boolean,
        isSaturdayChecked: Boolean,
        isSundayChecked: Boolean
    ): String {
        var selectedWorkingDays = ""
        if (isMondayChecked) {
            selectedWorkingDays += daysOfTheWeek[0]
        }
        if (isTuesdayChecked) {
            selectedWorkingDays += if (selectedWorkingDays == "") {
                daysOfTheWeek[1]
            }
            else {
                ", ${daysOfTheWeek[1]}"
            }
        }
        if (isWednesdayChecked) {
            selectedWorkingDays += if (selectedWorkingDays == "") {
                daysOfTheWeek[2]
            }
            else {
                ", ${daysOfTheWeek[2]}"
            }
        }
        if (isThursdayChecked) {
            selectedWorkingDays += if (selectedWorkingDays == "") {
                daysOfTheWeek[3]
            }
            else {
                ", ${daysOfTheWeek[3]}"
            }
        }
        if (isFridayChecked) {
            selectedWorkingDays += if (selectedWorkingDays == "") {
                daysOfTheWeek[4]
            }
            else {
                ", ${daysOfTheWeek[4]}"
            }
        }
        if (isSaturdayChecked) {
            selectedWorkingDays += if (selectedWorkingDays == "") {
                daysOfTheWeek[5]
            }
            else {
                ", ${daysOfTheWeek[5]}"
            }
        }
        if (isSundayChecked) {
            selectedWorkingDays += if (selectedWorkingDays == "") {
                daysOfTheWeek[6]
            }
            else {
                ", ${daysOfTheWeek[6]}"
            }
        }
        return selectedWorkingDays
    }

    private fun isBarbershopNameValid(barbershopName: String): Boolean {
        val isBarbershopNameValid = barbershopName.isNotEmpty()
        if (!isBarbershopNameValid) _uiState.update { it.copy(isBarbershopNameValid = false) }
        else _uiState.update { it.copy(isBarbershopNameValid = true) }
        return isBarbershopNameValid
    }

    private fun isPhoneValid(phone: String): Boolean {
        val phoneRegex = Regex("^06[0-6]/[0-9]{3}-[0-9]{3,4}$")
        val isPhoneValid = phoneRegex.matches(phone)
        if (!isPhoneValid) _uiState.update { it.copy(isPhoneValid = false) }
        else _uiState.update { it.copy(isPhoneValid = true) }
        return isPhoneValid
    }

    private fun isPriceValid(price: String): Boolean {
        val isPriceValid = price.toDoubleOrNull() != null
        if (!isPriceValid) _uiState.update { it.copy(isPriceValid = false) }
        else _uiState.update { it.copy(isPriceValid = true) }
        return isPriceValid
    }

    private fun isCountryValid(country: String): Boolean {
        val isCountryValid = country.isNotEmpty()
        if (!isCountryValid) _uiState.update { it.copy(isCountryValid = false) }
        else _uiState.update { it.copy(isCountryValid = true) }
        return isCountryValid
    }

    private fun isCityValid(city: String): Boolean {
        val isCityValid = city.isNotEmpty()
        if (!isCityValid) _uiState.update { it.copy(isCityValid = false) }
        else _uiState.update { it.copy(isCityValid = true) }
        return isCityValid
    }

    private fun isMunicipalityValid(municipality: String): Boolean {
        val isMunicipalityValid = municipality.isNotEmpty()
        if (!isMunicipalityValid) _uiState.update { it.copy(isMunicipalityValid = false) }
        else _uiState.update { it.copy(isMunicipalityValid = true) }
        return isMunicipalityValid
    }

    private fun isAddressValid(address: String): Boolean {
        val isAddressValid = address.isNotEmpty()
        if (!isAddressValid) _uiState.update { it.copy(isAddressValid = false) }
        else _uiState.update { it.copy(isAddressValid = true) }
        return isAddressValid
    }

    private fun areWorkingHoursValid(
        workingDayStartTime: String,
        workingDayEndTime: String
    ): Boolean {
        return ((workingDayEndTime > workingDayStartTime) || (workingDayStartTime == workingDayEndTime && workingDayStartTime == "00:00"))
                && (workingDayEndTime.substring(3, 5) == "00" || workingDayEndTime.substring(3, 5) == "30")
                && (workingDayStartTime.substring(3, 5) == "00" || workingDayStartTime.substring(3, 5) == "30")
    }

    private fun areSelectedWorkingDaysValid(selectedWorkingDays: String): Boolean {
        return selectedWorkingDays.isNotEmpty()
    }

}