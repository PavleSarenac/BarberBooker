package rs.ac.bg.etf.barberbooker.data.retrofit.apis

import retrofit2.http.Body
import retrofit2.http.POST
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.GoogleConnectRequest

const val GOOGLE_URL = "http://192.168.212.92:8080/google/"

interface GoogleApi {
    @POST("connect")
    suspend fun connect(@Body googleConnectRequest: GoogleConnectRequest)
}