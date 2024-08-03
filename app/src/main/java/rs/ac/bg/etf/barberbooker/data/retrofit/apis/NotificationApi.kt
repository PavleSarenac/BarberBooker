package rs.ac.bg.etf.barberbooker.data.retrofit.apis

import retrofit2.http.Body
import retrofit2.http.GET
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.NotificationData

const val NOTIFICATION_URL = "http://192.168.0.41:8080/notification/"

interface NotificationApi {

    @GET("sendNotification")
    suspend fun sendNotification(@Body notificationData: NotificationData)

}