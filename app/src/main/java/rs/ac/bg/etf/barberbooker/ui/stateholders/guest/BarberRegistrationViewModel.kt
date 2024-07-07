package rs.ac.bg.etf.barberbooker.ui.stateholders.guest

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.daysOfTheWeek
import rs.ac.bg.etf.barberbooker.data.room.entities.Barber
import rs.ac.bg.etf.barberbooker.data.room.repositories.BarberRepository
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import javax.inject.Inject

data class BarberRegistrationUiState(
    var email: String = "",
    var password: String = "",
    var barbershopName: String = "",
    var surname: String = "",
    var phone: String = "",

    var price: String = "",
    var country: String = "",
    var city: String = "",
    var municipality: String = "",
    var streetName: String = "",
    var streetNumber: String = "",

    var isEmailValid: Boolean = true,
    var isEmailAlreadyTaken: Boolean = false,
    var isPasswordValid: Boolean = true,
    var isBarbershopNameValid: Boolean = true,
    var isPhoneValid: Boolean = true,

    var isPriceValid: Boolean = true,
    var isCountryValid: Boolean = true,
    var isCityValid: Boolean = true,
    var isMunicipalityValid: Boolean = true,
    var isStreetNameValid: Boolean = true,
    var isStreetNumberValid: Boolean = true
)

@HiltViewModel
class BarberRegistrationViewModel @Inject constructor(
    private val barberRepository: BarberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(BarberRegistrationUiState())
    val uiState = _uiState

    fun setEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun setPassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

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

    fun setStreetName(streetName: String) {
        _uiState.update { it.copy(streetName = streetName) }
    }

    fun setStreetNumber(streetNumber: String) {
        _uiState.update { it.copy(streetNumber = streetNumber) }
    }

    fun registerBarber(
        coroutineScope: CoroutineScope,
        snackbarHostState: SnackbarHostState,
        navHostController: NavHostController,
        workingDayStartTime: String,
        workingDayEndTime: String,
        isMondayChecked: Boolean,
        isTuesdayChecked: Boolean,
        isWednesdayChecked: Boolean,
        isThursdayChecked: Boolean,
        isFridayChecked: Boolean,
        isSaturdayChecked: Boolean,
        isSundayChecked: Boolean
    ) {
        val email = _uiState.value.email
        val password = _uiState.value.password
        val barbershopName = _uiState.value.barbershopName
        val phone = _uiState.value.phone
        val price = _uiState.value.price
        val country = _uiState.value.country
        val city = _uiState.value.city
        val municipality = _uiState.value.municipality
        val streetName = _uiState.value.streetName
        val streetNumber = _uiState.value.streetNumber
        val selectedWorkingDays = getSelectedWorkingDays(
            isMondayChecked,
            isTuesdayChecked,
            isWednesdayChecked,
            isThursdayChecked,
            isFridayChecked,
            isSaturdayChecked,
            isSundayChecked,
        )
        coroutineScope.launch {
            if (!isDataValid(
                    email,
                    password,
                    barbershopName,
                    phone,
                    price,
                    country,
                    city,
                    municipality,
                    streetName,
                    streetNumber,
                    workingDayStartTime,
                    workingDayEndTime,
                    selectedWorkingDays
            )) {
                snackbarHostState.showSnackbar("Invalid data format!")
                return@launch
            }
            val isEmailAlreadyTaken = isEmailAlreadyTaken(email)
            if (isEmailAlreadyTaken) {
                snackbarHostState.showSnackbar("Email already taken!")
                return@launch
            }
            addNewBarber(
                email,
                password,
                barbershopName,
                phone,
                price,
                country,
                city,
                municipality,
                streetName,
                streetNumber
            )
            val snackbarResult = snackbarHostState.showSnackbar(
                message = "Registration successful!",
                withDismissAction = true,
                actionLabel = "Log in",
                duration = SnackbarDuration.Indefinite
            )
            if (snackbarResult == SnackbarResult.ActionPerformed) {
                navHostController.navigate(staticRoutes[1])
            }
        }
    }

    private fun addNewBarber(
        email: String,
        password: String,
        barbershopName: String,
        phone: String,
        price: String,
        country: String,
        city: String,
        municipality: String,
        streetName: String,
        streetNumber: String
    ) = viewModelScope.launch {
        val newBarber = Barber(
            0,
            email,
            password,
            barbershopName,
            price.toDouble(),
            phone,
            country,
            city,
            municipality,
            "$streetName $streetNumber",
            "",
            ""
        )
        //barberRepository.addNewBarber(newBarber)
        _uiState.update { BarberRegistrationUiState() }
    }

    private fun isDataValid(
        email: String,
        password: String,
        barbershopName: String,
        phone: String,
        price: String,
        country: String,
        city: String,
        municipality: String,
        streetName: String,
        streetNumber: String,
        workingDayStartTime: String,
        workingDayEndTime: String,
        selectedWorkingDays: String
    ): Boolean {
        var isDataValid = true
        if (!isEmailValid(email)) isDataValid = false
        if (!isPasswordValid(password)) isDataValid = false
        if (!isBarbershopNameValid(barbershopName)) isDataValid = false
        if (!isPhoneValid(phone)) isDataValid = false
        if (!isPriceValid(price)) isDataValid = false
        if (!isCountryValid(country)) isDataValid = false
        if (!isCityValid(city)) isDataValid = false
        if (!isMunicipalityValid(municipality)) isDataValid = false
        if (!isStreetNameValid(streetName)) isDataValid = false
        if (!isStreetNumberValid(streetNumber)) isDataValid = false
        if (!areWorkingHoursValid(workingDayStartTime, workingDayEndTime)) isDataValid = false

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
        if (isMondayChecked) selectedWorkingDays += "${daysOfTheWeek[0]};"
        if (isTuesdayChecked) selectedWorkingDays += "${daysOfTheWeek[1]};"
        if (isWednesdayChecked) selectedWorkingDays += "${daysOfTheWeek[2]};"
        if (isThursdayChecked) selectedWorkingDays += "${daysOfTheWeek[3]};"
        if (isFridayChecked) selectedWorkingDays += "${daysOfTheWeek[4]};"
        if (isSaturdayChecked) selectedWorkingDays += "${daysOfTheWeek[5]};"
        if (isSundayChecked) selectedWorkingDays += "${daysOfTheWeek[6]};"
        return selectedWorkingDays
    }

    private suspend fun isEmailAlreadyTaken(email: String): Boolean {
        val isEmailAlreadyTaken = barberRepository.isEmailAlreadyTaken(email)
        _uiState.update { it.copy(isEmailAlreadyTaken = isEmailAlreadyTaken) }
        return isEmailAlreadyTaken
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = Regex("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,}$", RegexOption.IGNORE_CASE)
        val isEmailValid = emailRegex.matches(email)
        if (!isEmailValid) _uiState.update { it.copy(isEmailValid = false) }
        else _uiState.update { it.copy(isEmailValid = true) }
        return isEmailValid
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordRegex = Regex("^(?=[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])(?=.*[A-Z])(?=.*[a-z].*[a-z].*[a-z]).{6,10}$")
        val isPasswordValid = passwordRegex.matches(password)
        if (!isPasswordValid) _uiState.update { it.copy(isPasswordValid = false) }
        else _uiState.update { it.copy(isPasswordValid = true) }
        return isPasswordValid
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

    private fun isStreetNameValid(streetName: String): Boolean {
        val isStreetNameValid = streetName.isNotEmpty()
        if (!isStreetNameValid) _uiState.update { it.copy(isStreetNameValid = false) }
        else _uiState.update { it.copy(isStreetNameValid = true) }
        return isStreetNameValid
    }

    private fun isStreetNumberValid(streetNumber: String): Boolean {
        val isStreetNumberValid = streetNumber.toIntOrNull() != null
        if (!isStreetNumberValid) _uiState.update { it.copy(isStreetNumberValid = false) }
        else _uiState.update { it.copy(isStreetNumberValid = true) }
        return isStreetNumberValid
    }

    private fun areWorkingHoursValid(
        workingDayStartTime: String,
        workingDayEndTime: String
    ): Boolean {
        return (((workingDayEndTime > workingDayStartTime) || (workingDayStartTime == workingDayEndTime && workingDayStartTime == "00:00"))
                        && (workingDayEndTime == "00" || workingDayEndTime == "30"))
    }

}