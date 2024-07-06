package rs.ac.bg.etf.barberbooker.data.room.entites

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Client(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var email: String,
    var password: String,
    var name: String,
    var surname: String,
    var phone: String
)