package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.client

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber.BarberProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientViewBarberProfileScreen(
    barberEmail: String,
    clientEmail: String,
    barberProfileViewModel: BarberProfileViewModel = hiltViewModel()
) {
    val uiState by barberProfileViewModel.uiState.collectAsState()
    val context = LocalContext.current
    var isBarberDataFetched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val job = barberProfileViewModel.fetchBarberData(barberEmail)
        job.join()
        isBarberDataFetched = true
    }

    if (!isBarberDataFetched) return

    val timePickerState = rememberTimePickerState(is24Hour = true)
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = barberProfileViewModel.getFirstValidDateInMillis(System.currentTimeMillis())
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            modifier = Modifier.width(1000.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Barbershop info",
                    fontWeight = FontWeight.Bold
                )
            }
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
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:${uiState.email}")
                        }
                        ContextCompat.startActivity(context, intent, null)
                    }
            ) {
                Icon(imageVector = Icons.Filled.Email, contentDescription = "Email address")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = uiState.email,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${uiState.phone}")
                        }
                        ContextCompat.startActivity(context, intent, null)
                    }
            ) {
                Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone number")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = uiState.phone,
                    textDecoration = TextDecoration.Underline,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            border = BorderStroke(1.dp, Color.White),
            modifier = Modifier.width(1000.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(imageVector = Icons.Filled.AccessTime, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Make a reservation",
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row {
                DatePicker(
                    state = datePickerState,
                    showModeToggle = false,
                    title = null,
                    colors = DatePickerDefaults.colors(
                        headlineContentColor = MaterialTheme.colorScheme.onPrimary,
                        todayContentColor = MaterialTheme.colorScheme.secondary,
                        todayDateBorderColor = MaterialTheme.colorScheme.onPrimary,
                        selectedDayContainerColor = MaterialTheme.colorScheme.secondary,
                        currentYearContentColor = MaterialTheme.colorScheme.secondary,
                        selectedYearContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledDayContentColor = MaterialTheme.colorScheme.secondary,
                    ),
                    dateValidator = barberProfileViewModel.dateValidator
                )
            }
            Divider()
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                TimePicker(
                    state = timePickerState,
                    modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp)
                )
            }
            Divider()
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        barberProfileViewModel.clientCreateReservationRequest(
                            barberProfileViewModel.convertDateMillisToString(datePickerState.selectedDateMillis!!),
                            String.format("%02d:%02d", timePickerState.hour, timePickerState.minute)
                        )
                    },
                    border = BorderStroke(1.dp, Color.White),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .padding(horizontal = 48.dp, vertical = 32.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Text(
                        text = "Submit request"
                    )
                }
            }
        }
    }
}