package rs.ac.bg.etf.barberbooker.data.room.repositories

import rs.ac.bg.etf.barberbooker.data.room.daos.BarberDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarberRepository @Inject constructor(private val barberDao: BarberDao) {

}