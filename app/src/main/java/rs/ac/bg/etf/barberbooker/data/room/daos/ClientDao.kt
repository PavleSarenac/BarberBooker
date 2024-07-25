package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.room.entities.tables.Client

@Dao
interface ClientDao {

    @Insert
    suspend fun addNewClient(client: Client)

    @Query("SELECT * FROM client WHERE email = :email")
    suspend fun getClientByEmail(email: String): Client?

    @Query("SELECT * FROM client WHERE email = :email AND password = :hashedPassword")
    suspend fun getClientByEmailAndPassword(email: String, hashedPassword: String): Client?

    @Query("UPDATE client SET name = :name, surname = :surname WHERE email = :email")
    suspend fun updateClientProfile(email: String, name: String, surname: String)

}