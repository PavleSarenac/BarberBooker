package rs.ac.bg.etf.barberbooker.ui.elements.screens.guest

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import rs.ac.bg.etf.barberbooker.data.staticRoutes

@Composable
fun InitialScreen(navHostController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "BarberBooker",
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 42.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(vertical = 32.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            onClick = { navHostController.navigate(staticRoutes[1]) },
            border = BorderStroke(1.dp, Color.White),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(horizontal = 64.dp)
                .fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                MaterialTheme.colorScheme.secondary,
                MaterialTheme.colorScheme.onSecondary
            )
        ) {
            Text(
                text = "Log in"
            )
        }
        OutlinedButton(
            onClick = { navHostController.navigate(staticRoutes[2]) },
            border = BorderStroke(1.dp, Color.White),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .padding(vertical = 32.dp, horizontal = 64.dp)
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