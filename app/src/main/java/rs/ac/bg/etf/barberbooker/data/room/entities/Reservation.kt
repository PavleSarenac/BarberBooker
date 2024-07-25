package rs.ac.bg.etf.barberbooker.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation")
data class Reservation(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var clientEmail: String,
    var barberEmail: String,
    var date: String,
    var time: String
)