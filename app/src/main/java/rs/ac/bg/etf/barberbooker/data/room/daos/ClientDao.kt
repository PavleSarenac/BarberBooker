package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.room.entities.Client

@Dao
interface ClientDao {

    @Insert
    suspend fun addNewClient(client: Client)

    @Query("SELECT * FROM client WHERE email = :email LIMIT 1")
    suspend fun getClient(email: String): Client?

}