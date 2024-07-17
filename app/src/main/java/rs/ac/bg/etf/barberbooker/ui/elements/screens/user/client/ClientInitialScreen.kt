package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ClientInitialScreen(clientEmail: String) {
    Text(text = clientEmail)
}