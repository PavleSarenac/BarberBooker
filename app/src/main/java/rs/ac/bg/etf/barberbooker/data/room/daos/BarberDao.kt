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
    suspend fun getBarber(email: String): Barber?

}