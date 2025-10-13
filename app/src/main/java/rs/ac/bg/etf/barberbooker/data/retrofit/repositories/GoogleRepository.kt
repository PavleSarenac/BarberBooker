package rs.ac.bg.etf.barberbooker.data.retrofit.repositories

import android.util.Log
import retrofit2.HttpException
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.GoogleApi
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.CreateGoogleCalendarEventRequest
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.GoogleConnectRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleRepository @Inject constructor(
    private val googleApi: GoogleApi
) {
    private val httpExceptionLogTag = "GoogleRepository"

    suspend fun connect(googleConnectRequest: GoogleConnectRequest) {
        try {
            googleApi.connect(googleConnectRequest)
        } catch (exception: HttpException) {
            Log.e(httpExceptionLogTag, exception.message())
        }
    }

    suspend fun createGoogleCalendarEvent(createGoogleCalendarEventRequest: CreateGoogleCalendarEventRequest) {
        try {
            googleApi.createGoogleCalendarEvent(createGoogleCalendarEventRequest)
        } catch (exception: HttpException) {
            Log.e(httpExceptionLogTag, exception.message())
        }
    }
}