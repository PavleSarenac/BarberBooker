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
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

const val REQUESTS_CHANNEL_ID = "REQUESTS_NOTIFICATIONS"
const val REQUESTS_CHANNEL_NAME = "Reservation requests"
const val REQUESTS_CHANNEL_DESCRIPTION = "New reservation requests"

const val REVIEWS_CHANNEL_ID = "REVIEWS_NOTIFICATIONS"
const val REVIEWS_CHANNEL_NAME = "Reviews"
const val REVIEWS_CHANNEL_DESCRIPTION = "New reviews"

@AndroidEntryPoint
class BarberBookerActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationsPermission()
        createRequestsNotificationChannel()
        createReviewsNotificationChannel()

        val notificationRoute = intent.getStringExtra("route") ?: ""

        setContent {
            BarberBookerTheme {
                BarberBookerApp(
                    notificationRoute = notificationRoute
                )
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

    private fun createRequestsNotificationChannel() {
        val notificationChannel =
            NotificationChannelCompat.Builder(REQUESTS_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_HIGH)
                .setName(REQUESTS_CHANNEL_NAME)
                .setDescription(REQUESTS_CHANNEL_DESCRIPTION).build()
        NotificationManagerCompat.from(this).createNotificationChannel(notificationChannel)
    }

    private fun createReviewsNotificationChannel() {
        val notificationChannel =
            NotificationChannelCompat.Builder(REVIEWS_CHANNEL_ID, NotificationManagerCompat.IMPORTANCE_HIGH)
                .setName(REVIEWS_CHANNEL_NAME)
                .setDescription(REVIEWS_CHANNEL_DESCRIPTION).build()
        NotificationManagerCompat.from(this).createNotificationChannel(notificationChannel)
    }
}