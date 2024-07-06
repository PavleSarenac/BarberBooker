package rs.ac.bg.etf.barberbooker.ui.elements.screens.guest

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import rs.ac.bg.etf.barberbooker.ui.stateholders.guest.ClientRegistrationViewModel

@Composable
fun SignUpAsClientScreen(
    clientRegistrationViewModel: ClientRegistrationViewModel = hiltViewModel()
) {
    val uiState by clientRegistrationViewModel.uiState.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.email,
            onValueChange =  { clientRegistrationViewModel.setEmail(it) }
        )
    }
}