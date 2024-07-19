package rs.ac.bg.etf.barberbooker.ui.elements.screens.guest.registration

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import rs.ac.bg.etf.barberbooker.ui.stateholders.guest.registration.BarberRegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpAsBarberScreen(
    navHostController: NavHostController,
    paddingValues: PaddingValues,
    snackbarHostState: SnackbarHostState,
    barberRegistrationViewModel: BarberRegistrationViewModel = hiltViewModel()
) {
    val uiState by barberRegistrationViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current

    val workingDayStartTimeState = rememberTimePickerState(is24Hour = true)
    val workingDayEndTimeState = rememberTimePickerState(is24Hour = true)

    val (mondayCheckedState, onMondayStateChange) = remember { mutableStateOf(true) }
    val (tuesdayCheckedState, onTuesdayStateChange) = remember { mutableStateOf(true) }
    val (wednesdayCheckedState, onWednesdayStateChange) = remember { mutableStateOf(true) }
    val (thursdayCheckedState, onThursdayStateChange) = remember { mutableStateOf(true) }
    val (fridayCheckedState, onFridayStateChange) = remember { mutableStateOf(true) }
    val (saturdayCheckedState, onSaturdayStateChange) = remember { mutableStateOf(false) }
    val (sundayCheckedState, onSundayStateChange) = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(color = MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = uiState.email,
            onValueChange =  { barberRegistrationViewModel.setEmail(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isEmailValid || uiState.isEmailAlreadyTaken,
            placeholder = { Text("e.g., milos@gmail.com") },
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange =  { barberRegistrationViewModel.setPassword(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isPasswordValid,
            placeholder = { Text("e.g., Milos123!") },
        )
        OutlinedTextField(
            value = uiState.barbershopName,
            onValueChange =  { barberRegistrationViewModel.setBarbershopName(it) },
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
        OutlinedTextField(
            value = uiState.phone,
            onValueChange =  { barberRegistrationViewModel.setPhone(it) },
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
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isPhoneValid,
            placeholder = { Text("e.g., 063/222-3333") },
        )
        OutlinedTextField(
            value = uiState.price,
            onValueChange =  { barberRegistrationViewModel.setPrice(it) },
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
            onValueChange =  { barberRegistrationViewModel.setCountry(it) },
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
            onValueChange =  { barberRegistrationViewModel.setCity(it) },
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
            onValueChange =  { barberRegistrationViewModel.setMunicipality(it) },
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
            value = uiState.streetName,
            onValueChange =  { barberRegistrationViewModel.setStreetName(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Street name") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
            keyboardActions = KeyboardActions(onNext = {
                focusManager.moveFocus(FocusDirection.Down)
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isStreetNameValid,
            placeholder = { Text("e.g., Marijane Gregoran") },
        )
        OutlinedTextField(
            value = uiState.streetNumber,
            onValueChange =  { barberRegistrationViewModel.setStreetNumber(it) },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            ),
            label = { Text(text = "Street number") },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            }),
            modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
            isError = !uiState.isStreetNumberValid,
            placeholder = { Text("e.g., 66") },
        )
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
        OutlinedButton(
            onClick = {
               barberRegistrationViewModel.registerBarber(
                   snackbarHostState,
                   navHostController,
                   String.format("%02d:%02d", workingDayStartTimeState.hour, workingDayStartTimeState.minute),
                   String.format("%02d:%02d", workingDayEndTimeState.hour, workingDayEndTimeState.minute),
                   mondayCheckedState,
                   tuesdayCheckedState,
                   wednesdayCheckedState,
                   thursdayCheckedState,
                   fridayCheckedState,
                   saturdayCheckedState,
                   sundayCheckedState
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
                text = "Sign up"
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
