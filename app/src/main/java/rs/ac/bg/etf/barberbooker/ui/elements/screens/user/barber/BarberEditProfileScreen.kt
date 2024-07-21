package rs.ac.bg.etf.barberbooker.ui.elements.screens.user.barber

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import rs.ac.bg.etf.barberbooker.data.daysOfTheWeek
import rs.ac.bg.etf.barberbooker.ui.stateholders.user.barber.BarberProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarberEditProfileScreen(
    barberEmail: String,
    snackbarHostState: SnackbarHostState,
    barberProfileViewModel: BarberProfileViewModel = hiltViewModel()
) {
    val snackbarCoroutineScope = rememberCoroutineScope()
    var isBarberDataFetched by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        val job = barberProfileViewModel.fetchBarberData(barberEmail)
        job.join()
        isBarberDataFetched = true
    }

    if (!isBarberDataFetched) return

    val uiState by barberProfileViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val workingDayStartTimeState = rememberTimePickerState(
        initialHour = uiState.workingHours.substring(0, 2).toInt(),
        initialMinute = uiState.workingHours.substring(3, 5).toInt(),
        is24Hour = true
    )
    val workingDayEndTimeState = rememberTimePickerState(
        initialHour = uiState.workingHours.substring(8, 10).toInt(),
        initialMinute = uiState.workingHours.substring(11, 13).toInt(),
        is24Hour = true
    )

    val (mondayCheckedState, onMondayStateChange) = remember { mutableStateOf(
        uiState.workingDays.contains(daysOfTheWeek[0])
    ) }
    val (tuesdayCheckedState, onTuesdayStateChange) = remember { mutableStateOf(
        uiState.workingDays.contains(daysOfTheWeek[1])
    ) }
    val (wednesdayCheckedState, onWednesdayStateChange) = remember { mutableStateOf(
        uiState.workingDays.contains(daysOfTheWeek[2])
    ) }
    val (thursdayCheckedState, onThursdayStateChange) = remember { mutableStateOf(
        uiState.workingDays.contains(daysOfTheWeek[3])
    ) }
    val (fridayCheckedState, onFridayStateChange) = remember { mutableStateOf(
        uiState.workingDays.contains(daysOfTheWeek[4])
    ) }
    val (saturdayCheckedState, onSaturdayStateChange) = remember { mutableStateOf(
        uiState.workingDays.contains(daysOfTheWeek[5])
    ) }
    val (sundayCheckedState, onSundayStateChange) = remember { mutableStateOf(
        uiState.workingDays.contains(daysOfTheWeek[6])
    ) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 24.dp)
            .background(color = MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.barbershopName,
            onValueChange =  { barberProfileViewModel.setBarbershopName(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Barbershop name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isBarbershopNameValid,
            placeholder = { Text("e.g., Cut&Go") }
        )
        Text(
            text = "Working days:",
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            fontSize = 24.sp
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 8.dp)
                .toggleable(
                    value = mondayCheckedState,
                    onValueChange = { onMondayStateChange(!mondayCheckedState) },
                    role = Role.Checkbox
                )
        ) {
            Checkbox(
                checked = mondayCheckedState,
                onCheckedChange = null
            )
            Text(
                text = "Monday"
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 8.dp)
                .toggleable(
                    value = tuesdayCheckedState,
                    onValueChange = { onTuesdayStateChange(!tuesdayCheckedState) },
                    role = Role.Checkbox
                )
        ) {
            Checkbox(
                checked = tuesdayCheckedState,
                onCheckedChange = null
            )
            Text(
                text = "Tuesday"
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 8.dp)
                .toggleable(
                    value = wednesdayCheckedState,
                    onValueChange = { onWednesdayStateChange(!wednesdayCheckedState) },
                    role = Role.Checkbox
                )
        ) {
            Checkbox(
                checked = wednesdayCheckedState,
                onCheckedChange = null
            )
            Text(
                text = "Wednesday"
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 8.dp)
                .toggleable(
                    value = thursdayCheckedState,
                    onValueChange = { onThursdayStateChange(!thursdayCheckedState) },
                    role = Role.Checkbox
                )
        ) {
            Checkbox(
                checked = thursdayCheckedState,
                onCheckedChange = null
            )
            Text(
                text = "Thursday"
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 8.dp)
                .toggleable(
                    value = fridayCheckedState,
                    onValueChange = { onFridayStateChange(!fridayCheckedState) },
                    role = Role.Checkbox
                )
        ) {
            Checkbox(
                checked = fridayCheckedState,
                onCheckedChange = null
            )
            Text(
                text = "Friday"
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 8.dp)
                .toggleable(
                    value = saturdayCheckedState,
                    onValueChange = { onSaturdayStateChange(!saturdayCheckedState) },
                    role = Role.Checkbox
                )
        ) {
            Checkbox(
                checked = saturdayCheckedState,
                onCheckedChange = null
            )
            Text(
                text = "Saturday"
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 48.dp, vertical = 8.dp)
                .toggleable(
                    value = sundayCheckedState,
                    onValueChange = { onSundayStateChange(!sundayCheckedState) },
                    role = Role.Checkbox
                )
        ) {
            Checkbox(
                checked = sundayCheckedState,
                onCheckedChange = null
            )
            Text(
                text = "Sunday"
            )
        }
        Text(
            text = "Working day start time:",
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            fontSize = 24.sp
        )
        TimePicker(
            state = workingDayStartTimeState,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(
            text = "Working day end time:",
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            fontSize = 24.sp
        )
        TimePicker(
            state = workingDayEndTimeState,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = uiState.price,
            onValueChange =  { barberProfileViewModel.setPrice(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Price") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isPriceValid,
            placeholder = { Text("e.g., 1199.99") },
        )
        OutlinedTextField(
            value = uiState.country,
            onValueChange =  { barberProfileViewModel.setCountry(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Country") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isCountryValid,
            placeholder = { Text("e.g., Serbia") },
        )
        OutlinedTextField(
            value = uiState.city,
            onValueChange =  { barberProfileViewModel.setCity(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "City") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isCityValid,
            placeholder = { Text("e.g., Belgrade") },
        )
        OutlinedTextField(
            value = uiState.municipality,
            onValueChange =  { barberProfileViewModel.setMunicipality(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Municipality") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isMunicipalityValid,
            placeholder = { Text("e.g., Karaburma") },
        )
        OutlinedTextField(
            value = uiState.address,
            onValueChange =  { barberProfileViewModel.setAddress(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Address") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isAddressValid,
            placeholder = { Text("e.g., Marijane Gregoran 68") },
        )
        OutlinedTextField(
            value = uiState.phone,
            onValueChange =  { barberProfileViewModel.setPhone(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Phone") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isPhoneValid,
            placeholder = { Text("e.g., 063/222-3333") },
        )
        OutlinedButton(
            onClick = {
                barberProfileViewModel.updateProfile(
                    snackbarHostState,
                    String.format("%02d:%02d", workingDayStartTimeState.hour, workingDayStartTimeState.minute),
                    String.format("%02d:%02d", workingDayEndTimeState.hour, workingDayEndTimeState.minute),
                    mondayCheckedState,
                    tuesdayCheckedState,
                    wednesdayCheckedState,
                    thursdayCheckedState,
                    fridayCheckedState,
                    saturdayCheckedState,
                    sundayCheckedState,
                    snackbarCoroutineScope
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
                text = "Submit"
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}