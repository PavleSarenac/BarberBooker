package rs.ac.bg.etf.barberbooker.data.retrofit.repositories

import rs.ac.bg.etf.barberbooker.data.retrofit.apis.NotificationApi
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.NotificationData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationRepository @Inject constructor(
    private val notificationApi: NotificationApi
) {

    suspend fun sendNotification(notificationData: NotificationData) {
        notificationApi.sendNotification(notificationData)
    }

}