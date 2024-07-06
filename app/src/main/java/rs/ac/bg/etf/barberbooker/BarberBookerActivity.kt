package rs.ac.bg.etf.barberbooker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import rs.ac.bg.etf.barberbooker.ui.elements.theme.BarberBookerTheme

class BarberBookerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarberBookerTheme {

            }
        }
    }
}