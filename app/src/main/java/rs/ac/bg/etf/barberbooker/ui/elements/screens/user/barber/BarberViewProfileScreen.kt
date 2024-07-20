package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber.BarberViewProfileViewModel

@Composable
fun BarberViewProfileScreen(
    barberEmail: String,
    barberViewProfileViewModel: BarberViewProfileViewModel = hiltViewModel()
) {
    val uiState by barberViewProfileViewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        barberViewProfileViewModel.fetchBarberData(barberEmail)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = Modifier.width(300.dp)
        ) {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.ContentCut, contentDescription = "Barbershop name")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = uiState.barbershopName)
            }
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.Flag, contentDescription = "Country")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = uiState.country)
            }
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.LocationCity, contentDescription = "City")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = uiState.city)
            }
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.Nature, contentDescription = "Municipality")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = uiState.municipality)
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        val googleMapsAddress = uiState.address
                            .replace(" ", "%20")
                            .replace(",", "%2C")
                        val googleMapsLocation = "${uiState.country}%2C%20" +
                                    "${uiState.city}%2C%20" +
                                    "${uiState.municipality}%2C%20" +
                                    googleMapsAddress
                        val intent = Intent().apply {
                            action = Intent.ACTION_VIEW
                            data = Uri.parse("geo:0,0?q=${googleMapsLocation}")
                        }
                        ContextCompat.startActivity(context, intent, null)
                    }
            ) {
                Icon(imageVector = Icons.Filled.LocationOn, contentDescription = "Address")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = uiState.address,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.AttachMoney, contentDescription = "Price")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${uiState.price} RSD")
            }
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "Email address")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = uiState.email)
            }
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone number")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = uiState.phone)
            }
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.CalendarMonth, contentDescription = "Working days")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = uiState.workingDays)
            }
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(imageVector = Icons.Filled.AccessTime, contentDescription = "Working hours")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = uiState.workingHours)
            }
        }
    }
}