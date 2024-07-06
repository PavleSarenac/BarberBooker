package rs.ac.bg.etf.barberbooker.data.room.repositories

import rs.ac.bg.etf.barberbooker.data.room.daos.ClientDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClientRepository @Inject constructor(private val clientDao: ClientDao) {

}