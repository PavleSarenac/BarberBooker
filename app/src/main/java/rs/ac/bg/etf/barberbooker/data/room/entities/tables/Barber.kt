package rs.ac.bg.etf.barberbooker.data.room.entities.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "barber")
data class Barber(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var email: String,
    var password: String,
    var barbershopName: String,
    var price: Double,
    var phone: String,
    var country: String,
    var city: String,
    var municipality: String,
    var address: String,
    var workingDays: String,
    var workingHours: String
)