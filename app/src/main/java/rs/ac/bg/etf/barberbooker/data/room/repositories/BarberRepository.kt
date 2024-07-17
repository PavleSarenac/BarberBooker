package rs.ac.bg.etf.barberbooker.data.room.repositories

import rs.ac.bg.etf.barberbooker.data.room.daos.BarberDao
import rs.ac.bg.etf.barberbooker.data.room.entities.Barber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarberRepository @Inject constructor(private val barberDao: BarberDao) {

    suspend fun addNewBarber(barber: Barber) {
        barberDao.addNewBarber(barber)
    }

    suspend fun isEmailAlreadyTaken(email: String): Boolean {
        val barber = barberDao.getBarberByEmail(email)
        return barber != null
    }

    suspend fun areLoginCredentialsValid(email: String, hashedPassword: String): Boolean {
        val barber = barberDao.getBarberByEmailAndPassword(email, hashedPassword)
        return barber != null
    }
}