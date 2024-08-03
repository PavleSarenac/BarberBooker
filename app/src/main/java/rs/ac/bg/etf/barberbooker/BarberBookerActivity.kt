package rs.ac.bg.etf.barberbooker

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.content.ContextCompat
import dagger.hilt.android.AndroidEntryPoint
import rs.ac.bg.etf.barberbooker.ui.elements.BarberBookerApp
import rs.ac.bg.etf.barberbooker.ui.elements.theme.BarberBookerTheme
import android.Manifest.permission.POST_NOTIFICATIONS
import androidx.core.app.ActivityCompat

@AndroidEntryPoint
class BarberBookerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationsPermission()
        setContent {
            BarberBookerTheme {
                BarberBookerApp()
            }
        }
    }

    private fun requestNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
}