package rs.ac.bg.etf.barberbooker.data.room.repositories

import rs.ac.bg.etf.barberbooker.data.room.daos.ClientDao
import rs.ac.bg.etf.barberbooker.data.room.entities.Client
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepository @Inject constructor(private val clientDao: ClientDao) {
    suspend fun addNewClient(client: Client) {
        clientDao.addNewClient(client)
    }

    suspend fun isEmailAlreadyTaken(email: String): Boolean {
        val client = clientDao.getClient(email)
        return client != null
    }
}