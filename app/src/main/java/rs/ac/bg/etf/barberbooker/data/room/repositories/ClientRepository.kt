package rs.ac.bg.etf.barberbooker.data.room.repositories

import rs.ac.bg.etf.barberbooker.data.room.daos.ClientDao
import rs.ac.bg.etf.barberbooker.data.room.entities.tables.Client
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

    suspend fun getClientByEmail(email: String): Client? {
        return clientDao.getClientByEmail(email)
    }

    suspend fun updateClientProfile(email: String, name: String, surname: String) {
        clientDao.updateClientProfile(email, name, surname)
    }

}