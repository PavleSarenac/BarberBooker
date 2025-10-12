package rs.ac.bg.etf.barberbooker.data.retrofit.repositories

import rs.ac.bg.etf.barberbooker.data.retrofit.apis.GoogleApi
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.GoogleConnectRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleRepository @Inject constructor(
    private val googleApi: GoogleApi
) {
    suspend fun connect(googleConnectRequest: GoogleConnectRequest) {
        googleApi.connect(googleConnectRequest)
    }
}