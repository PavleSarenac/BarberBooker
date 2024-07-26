package rs.ac.bg.etf.barberbooker.data.room.entities.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class Review(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var clientEmail: String,
    var barberEmail: String,
    var grade: Int,
    var text: String,
    var date: String
)