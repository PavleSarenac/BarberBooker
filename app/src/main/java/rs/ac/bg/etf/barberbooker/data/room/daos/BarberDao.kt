package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.room.entities.Barber

@Dao
interface BarberDao {

    @Query("SELECT * FROM barber WHERE email = :email LIMIT 1")
    suspend fun getBarber(email: String): Barber?

}