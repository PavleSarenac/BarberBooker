package rs.ac.bg.etf.barberbooker

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.messaging.FirebaseMessagingService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.FcmTokenUpdateData
import rs.ac.bg.etf.barberbooker.data.retrofit.repositories.BarberRepository
import rs.ac.bg.etf.barberbooker.data.retrofit.repositories.ClientRepository
import javax.inject.Inject

class PushNotificationService: FirebaseMessagingService() {

    @Inject
    lateinit var barberRepository: BarberRepository

    @Inject
    lateinit var clientRepository: ClientRepository

    private val serviceJob = Job()
    private val serviceScope = CoroutineScope(Dispatchers.IO + serviceJob)

    /*
    * "token" needs to be sent to the server so that the token in the database for the
    * currently logged in user can be updated so that push notifications can be sent from
    * the server to that specific user even though the token has changed
    * */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        serviceScope.launch(Dispatchers.IO) {
            val sharedPreferences: SharedPreferences = getSharedPreferences("login_data", Context.MODE_PRIVATE)
            val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
            val userEmail = sharedPreferences.getString("user_email", "")
            val userType = sharedPreferences.getString("user_type", "")

            if (isLoggedIn) {
                if (userType == "barber") {
                    barberRepository.updateFcmToken(
                        FcmTokenUpdateData(
                            email = userEmail!!,
                            fcmToken = token
                        )
                    )
                } else {
                    clientRepository.updateFcmToken(
                        FcmTokenUpdateData(
                            email = userEmail!!,
                            fcmToken = token
                        )
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

}