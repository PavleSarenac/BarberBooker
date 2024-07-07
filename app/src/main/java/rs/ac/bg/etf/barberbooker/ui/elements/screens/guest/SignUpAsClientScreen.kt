package rs.ac.bg.etf.barberbooker.ui.elements.screens.guest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import rs.ac.bg.etf.barberbooker.ui.stateholders.guest.ClientRegistrationViewModel

@Composable
fun SignUpAsClientScreen(
    clientRegistrationViewModel: ClientRegistrationViewModel = hiltViewModel()
) {
    val uiState by clientRegistrationViewModel.uiState.collectAsState()
    val focusManager = LocalFocusManager.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp)
            .background(color = MaterialTheme.colorScheme.primary)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign up as client",
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(vertical = 32.dp)
        )
        OutlinedTextField(
            value = uiState.email,
            onValueChange =  { clientRegistrationViewModel.setEmail(it) },
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
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = uiState.password,
            onValueChange =  { clientRegistrationViewModel.setPassword(it) },
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
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = uiState.name,
            onValueChange =  { clientRegistrationViewModel.setName(it) },
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
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = uiState.surname,
            onValueChange =  { clientRegistrationViewModel.setSurname(it) },
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
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = uiState.phone,
            onValueChange =  { clientRegistrationViewModel.setPhone(it) },
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
            modifier = Modifier.padding(vertical = 8.dp)
        )
        OutlinedButton(
            onClick = { clientRegistrationViewModel.addNewClient() },
            border = BorderStroke(1.dp, Color.White),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(vertical = 32.dp)
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