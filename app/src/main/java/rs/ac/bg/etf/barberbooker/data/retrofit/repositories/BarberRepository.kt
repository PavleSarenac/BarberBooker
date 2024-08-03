package rs.ac.bg.etf.barberbooker.data.retrofit.repositories

import rs.ac.bg.etf.barberbooker.data.retrofit.apis.BarberApi
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedBarberWithAverageGrade
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.FcmTokenUpdateData
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.tables.Barber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BarberRepository @Inject constructor(
    private val barberApi: BarberApi
) {

    suspend fun addNewBarber(barber: Barber) {
        barberApi.addNewBarber(barber)
    }

    suspend fun isEmailAlreadyTaken(email: String): Boolean {
        val response = barberApi.getBarberByEmail(email)
        return response.isSuccessful
    }

    suspend fun areLoginCredentialsValid(email: String, hashedPassword: String): Boolean {
        val response = barberApi.getBarberByEmailAndPassword(email, hashedPassword)
        return response.isSuccessful
    }

    suspend fun getBarberByEmail(email: String): Barber? {
        val response = barberApi.getBarberByEmail(email)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
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
        barberApi.updateBarberProfile(
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

    suspend fun getSearchResults(query: String): List<ExtendedBarberWithAverageGrade> {
        return barberApi.getSearchResults(query)
    }

    suspend fun updateFcmToken(fcmTokenUpdateData: FcmTokenUpdateData) {
        barberApi.updateFcmToken(fcmTokenUpdateData)
    }

}