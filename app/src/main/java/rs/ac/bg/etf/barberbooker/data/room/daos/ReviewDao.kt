package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedReviewWithClient
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

    @Query("""
        SELECT 
            r.id AS reviewId,
            r.clientEmail,
            r.barberEmail,
            r.grade,
            r.text,
            r.date,
            c.id AS clientId,
            c.name AS clientName,
            c.surname AS clientSurname
        FROM review r
        INNER JOIN client c ON c.email = r.clientEmail
        WHERE barberEmail = :barberEmail
    """)
    suspend fun getBarberReviews(
        barberEmail: String
    ): List<ExtendedReviewWithClient>

}