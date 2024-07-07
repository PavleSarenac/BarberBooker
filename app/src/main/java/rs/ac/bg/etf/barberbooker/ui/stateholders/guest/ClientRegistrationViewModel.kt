package rs.ac.bg.etf.barberbooker.ui.stateholders.guest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.room.entities.Client
import rs.ac.bg.etf.barberbooker.data.room.repositories.ClientRepository
import javax.inject.Inject

data class ClientRegistrationUiState(
    var email: String = "",
    var password: String = "",
    var name: String = "",
    var surname: String = "",
    var phone: String = "",
    var isEmailValid: Boolean = true,
    var isEmailAlreadyTaken: Boolean = false,
    var isPasswordValid: Boolean = true,
    var isNameValid: Boolean = true,
    var isSurnameValid: Boolean = true,
    var isPhoneValid: Boolean = true
)

@HiltViewModel
class ClientRegistrationViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ViewModel() {

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

    suspend fun isEmailAlreadyTaken(email: String): Boolean {
        val isEmailAlreadyTaken = clientRepository.isEmailAlreadyTaken(email)
        _uiState.update { it.copy(isEmailAlreadyTaken = isEmailAlreadyTaken) }
        return isEmailAlreadyTaken
    }

    fun addNewClient() = viewModelScope.launch {
        val email = _uiState.value.email
        val password = _uiState.value.password
        val name = _uiState.value.name
        val surname = _uiState.value.surname
        val phone = _uiState.value.phone
        if (isDataValid(email, password, name, surname, phone)) {
            val newClient = Client(0, email, password, name, surname, phone)
            clientRepository.addNewClient(newClient)
            _uiState.update { ClientRegistrationUiState() }
        }
    }

    fun isDataValid(
        email: String,
        password: String,
        name: String,
        surname: String,
        phone: String
    ): Boolean {
        var isDataValid = true
        if (!isEmailValid(email)) isDataValid = false
        if (!isPasswordValid(password)) isDataValid = false
        if (!isNameValid(name)) isDataValid = false
        if (!isSurnameValid(surname)) isDataValid = false
        if (!isPhoneValid(phone)) isDataValid = false
        return isDataValid
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

    private fun isNameValid(name: String): Boolean {
        val isNameValid = name.isNotEmpty()
        if (!isNameValid) _uiState.update { it.copy(isNameValid = false) }
        else _uiState.update { it.copy(isNameValid = true) }
        return isNameValid
    }

    private fun isSurnameValid(surname: String): Boolean {
        val isSurnameValid = surname.isNotEmpty()
        if (!isSurnameValid) _uiState.update { it.copy(isSurnameValid = false) }
        else _uiState.update { it.copy(isSurnameValid = true) }
        return isSurnameValid
    }

    private fun isPhoneValid(phone: String): Boolean {
        val phoneRegex = Regex("^06[0-6]/[0-9]{3}-[0-9]{3,4}$")
        val isPhoneValid = phoneRegex.matches(phone)
        if (!isPhoneValid) _uiState.update { it.copy(isPhoneValid = false) }
        else _uiState.update { it.copy(isPhoneValid = true) }
        return isPhoneValid
    }

}