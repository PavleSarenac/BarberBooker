package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.room.entities.tables.Review

@Dao
interface ReviewDao {

    @Insert
    suspend fun submitReview(review: Review)

    @Query("""
        SELECT * FROM review
        WHERE clientEmail = :clientEmail AND barberEmail = :barberEmail
    """)
    suspend fun getClientReviewsForBarber(
        clientEmail: String,
        barberEmail: String
    ): List<Review>

}