package rs.ac.bg.etf.barberbooker

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class PushNotificationService: FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        /*
        * TODO
        * "token" needs to be sent to the server so that the token in the database for the
        * currently logged in user can be updated so that push notifications can be sent from
        * the server to that specific user even though the token has changed
        * */
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        // This method can be used for defining a response to receiving the notification from Firebase.
        // Probably won't be necessary for this app.
    }

}