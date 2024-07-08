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
        val client = clientDao.getClientByEmail(email)
        return client != null
    }

    suspend fun areLoginCredentialsValid(email: String, hashedPassword: String): Boolean {
        val client = clientDao.getClientByEmailAndPassword(email, hashedPassword)
        return client != null
    }
}