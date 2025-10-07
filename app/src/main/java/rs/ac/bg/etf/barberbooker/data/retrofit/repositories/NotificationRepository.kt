package rs.ac.bg.etf.barberbooker.data.retrofit.repositories

import android.util.Log
import retrofit2.HttpException
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.NotificationApi
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.NotificationData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationApi: NotificationApi
) {

    suspend fun sendNotification(notificationData: NotificationData) {
        try {
            notificationApi.sendNotification(notificationData)
        } catch (e: HttpException){
            Log.e("NotificationRepository", "Notification sending has failed: ${e.response()}")
        }
    }

}