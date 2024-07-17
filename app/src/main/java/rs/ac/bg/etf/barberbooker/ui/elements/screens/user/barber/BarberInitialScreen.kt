package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BarberInitialScreen(barberEmail: String) {
    Text(text = barberEmail)
}