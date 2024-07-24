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

    suspend fun getBarberByEmail(email: String): Barber? {
        return barberDao.getBarberByEmail(email)
    }

    suspend fun updateBarberProfile(
        email: String,
        barbershopName: String,
        price: Double,
        phone: String,
        country: String,
        city: String,
        municipality: String,
        address: String,
        workingDays: String,
        workingHours: String
    ) {
        barberDao.updateBarberProfile(
            email,
            barbershopName,
            price,
            phone,
            country,
            city,
            municipality,
            address,
            workingDays,
            workingHours
        )
    }

    suspend fun getSearchResults(query: String): List<Barber> {
        val queryParameters = query.split(" ")
        val searchResults = mutableListOf<Barber>()

        queryParameters.forEach {
            searchResults.addAll(barberDao.getSearchResults("%$it%"))
        }

        return searchResults.distinct()
    }

}