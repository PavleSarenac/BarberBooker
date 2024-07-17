package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.room.entities.Barber
import rs.ac.bg.etf.barberbooker.data.room.entities.Client

@Dao
interface BarberDao {

    @Insert
    suspend fun addNewBarber(barber: Barber)

    @Query("SELECT * FROM barber WHERE email = :email")
    suspend fun getBarberByEmail(email: String): Barber?

    @Query("SELECT * FROM client WHERE email = :email AND password = :hashedPassword")
    suspend fun getBarberByEmailAndPassword(email: String, hashedPassword: String): Client?


}