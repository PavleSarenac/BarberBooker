package rs.ac.bg.etf.barberbooker.ui.stateholders.user.client

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import rs.ac.bg.etf.barberbooker.data.room.entities.tables.Barber
import rs.ac.bg.etf.barberbooker.data.room.repositories.BarberRepository
import java.text.DecimalFormat
import javax.inject.Inject

data class ClientSearchBarbersUiState(
    var query: String = "",
    var searchResults: List<Barber> = listOf()
)

@HiltViewModel
class ClientSearchBarbersViewModel @Inject constructor(
    private val barberRepository: BarberRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ClientSearchBarbersUiState())
    val uiState = _uiState

    val decimalFormat = DecimalFormat("#.00")

    fun setQuery(query: String) {
        _uiState.update { it.copy(query = query) }
    }

    fun getSearchResults() = viewModelScope.launch(Dispatchers.IO) {
        val query = _uiState.value.query
        val searchResults = barberRepository.getSearchResults(query)
        withContext(Dispatchers.Main) {
            _uiState.update { it.copy(searchResults = searchResults) }
        }
    }

    fun getSortedSearchResults(
        sortingByNameState: Boolean,
        sortingByNameAscendingState: Boolean,
        sortingByNameDescendingState: Boolean,
        sortingByPriceState: Boolean,
        sortingByPriceAscendingState: Boolean,
        sortingByPriceDescendingState: Boolean
    ): List<Barber> {
        var sortedSearchResults = _uiState.value.searchResults

        if (sortingByNameState) {
            if (sortingByNameAscendingState) {
                sortedSearchResults = sortedSearchResults.sortedBy { it.barbershopName }
            }
            if (sortingByNameDescendingState) {
                sortedSearchResults = sortedSearchResults.sortedByDescending { it.barbershopName }
            }
        }

        if (sortingByPriceState) {
            if (sortingByPriceAscendingState) {
                sortedSearchResults = sortedSearchResults.sortedBy { it.price }
            }
            if (sortingByPriceDescendingState) {
                sortedSearchResults = sortedSearchResults.sortedByDescending { it.price }
            }
        }

        return sortedSearchResults
    }

}