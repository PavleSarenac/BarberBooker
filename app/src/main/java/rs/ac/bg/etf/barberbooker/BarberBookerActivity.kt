package rs.ac.bg.etf.barberbooker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import rs.ac.bg.etf.barberbooker.ui.elements.BarberBookerApp
import rs.ac.bg.etf.barberbooker.ui.elements.theme.BarberBookerTheme

@AndroidEntryPoint
class BarberBookerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BarberBookerTheme {
                BarberBookerApp()
            }
        }
    }
}