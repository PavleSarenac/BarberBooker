package rs.ac.bg.etf.barberbooker.ui.elements.screens.guest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.staticRoutes
import rs.ac.bg.etf.barberbooker.ui.stateholders.guest.BarberRegistrationUiState
import rs.ac.bg.etf.barberbooker.ui.stateholders.guest.BarberRegistrationViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpAsBarberScreen(
    navHostController: NavHostController,
    barberRegistrationViewModel: BarberRegistrationViewModel = hiltViewModel()
) {
    val uiState by barberRegistrationViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val workingDayStartTimeState = rememberTimePickerState(is24Hour = true)
    val workingDayEndTimeState = rememberTimePickerState(is24Hour = true)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary
                ),
                title = {
                    Text(
                        text = "Sign up as barber",
                        fontWeight = FontWeight.ExtraBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Go back to previous screen"
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = MaterialTheme.colorScheme.primary)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
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
                value = uiState.name,
                onValueChange =  { barberRegistrationViewModel.setName(it) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                label = { Text(text = "Name") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
                isError = !uiState.isNameValid,
                placeholder = { Text("e.g., Milos") }
            )
            OutlinedTextField(
                value = uiState.surname,
                onValueChange =  { barberRegistrationViewModel.setSurname(it) },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
                    cursorColor = MaterialTheme.colorScheme.onPrimary,
                    focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
                ),
                label = { Text(text = "Surname") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next,
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }),
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
                isError = !uiState.isSurnameValid,
                placeholder = { Text("e.g., Milosevic") }
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
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                }),
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp),
                isError = !uiState.isPriceValid,
                placeholder = { Text("e.g., 1199.99") },
            )
            Text(
                text = "Working day start time:",
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp)
            )
            TimePicker(
                state = workingDayStartTimeState,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = "Working day end time:",
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 8.dp)
            )
            TimePicker(
                state = workingDayEndTimeState,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            OutlinedButton(
                onClick = {
                    registerBarber(
                        barberRegistrationViewModel,
                        uiState,
                        coroutineScope,
                        snackbarHostState,
                        navHostController
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
        }
    }
}

fun registerBarber(
    barberRegistrationViewModel: BarberRegistrationViewModel,
    uiState: BarberRegistrationUiState,
    coroutineScope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
    coroutineScope.launch {
        if (!barberRegistrationViewModel.isDataValid(
                uiState.email,
                uiState.password,
                uiState.name,
                uiState.surname,
                uiState.phone
            )) {
            snackbarHostState.showSnackbar("Invalid data format!")
            return@launch
        }
        val isEmailAlreadyTaken = barberRegistrationViewModel.isEmailAlreadyTaken(uiState.email)
        if (isEmailAlreadyTaken) {
            snackbarHostState.showSnackbar("Email already taken!")
            return@launch
        }
        barberRegistrationViewModel.addNewBarber()
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