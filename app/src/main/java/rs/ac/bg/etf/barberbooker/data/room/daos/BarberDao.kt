package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.room.entities.Barber

@Dao
interface BarberDao {

    @Insert
    suspend fun addNewBarber(barber: Barber)

    @Query("SELECT * FROM barber WHERE email = :email")
    suspend fun getBarberByEmail(email: String): Barber?

    @Query("SELECT * FROM barber WHERE email = :email AND password = :hashedPassword")
    suspend fun getBarberByEmailAndPassword(email: String, hashedPassword: String): Barber?

    @Query("""
        UPDATE barber 
        SET barbershopName = :barbershopName, 
            price = :price, 
            phone = :phone, 
            country = :country, 
            city = :city, 
            municipality = :municipality, 
            address = :address, 
            workingDays = :workingDays, 
            workingHours = :workingHours
        WHERE email = :email
    """)
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
    )

}