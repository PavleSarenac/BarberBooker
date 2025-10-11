package rs.ac.bg.etf.barberbooker.data.retrofit.repositories

import rs.ac.bg.etf.barberbooker.data.retrofit.apis.ClientApi
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.FcmTokenUpdateData
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.tables.Client
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepository @Inject constructor(
    private val clientApi: ClientApi
) {
    suspend fun addNewClient(client: Client) {
        clientApi.addNewClient(client)
    }

    suspend fun isEmailAlreadyTaken(email: String): Boolean {
        val response = clientApi.getClientByEmail(email)
        return response.isSuccessful
    }

    suspend fun getClientByEmail(email: String): Client? {
        val response = clientApi.getClientByEmail(email)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }

    suspend fun updateClientProfile(email: String, name: String, surname: String) {
        clientApi.updateClientProfile(email, name, surname)
    }

    suspend fun updateFcmToken(fcmTokenUpdateData: FcmTokenUpdateData) {
        clientApi.updateFcmToken(fcmTokenUpdateData)
    }
}