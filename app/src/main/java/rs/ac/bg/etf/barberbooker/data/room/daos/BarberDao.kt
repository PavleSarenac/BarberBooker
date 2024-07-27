package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedBarberWithAverageGrade
import rs.ac.bg.etf.barberbooker.data.room.entities.tables.Barber

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

    @Query("""
        SELECT b.*, COALESCE(AVG(r.grade), 0.00) AS averageGrade
        FROM barber b
        LEFT JOIN review r ON b.email = r.barberEmail
        WHERE LOWER(b.barbershopName) LIKE LOWER(:queryParameter)
            OR LOWER(b.country) LIKE LOWER(:queryParameter)
            OR LOWER(b.city) LIKE LOWER(:queryParameter)
            OR LOWER(b.municipality) LIKE LOWER(:queryParameter)
            OR LOWER(b.address) LIKE LOWER(:queryParameter)
        GROUP BY b.email
    """)
    suspend fun getSearchResults(queryParameter: String): List<ExtendedBarberWithAverageGrade>

}