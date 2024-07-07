package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import rs.ac.bg.etf.barberbooker.data.room.entites.Client

@Dao
interface ClientDao {
    @Insert
    suspend fun addNewClient(client: Client)
}