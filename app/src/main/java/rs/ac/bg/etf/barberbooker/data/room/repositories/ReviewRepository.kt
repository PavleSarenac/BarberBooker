package rs.ac.bg.etf.barberbooker.data.room.repositories

import rs.ac.bg.etf.barberbooker.data.room.daos.ReviewDao
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedReviewWithClient
import rs.ac.bg.etf.barberbooker.data.room.entities.tables.Review
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(
    private val reviewDao: ReviewDao
) {

    suspend fun submitReview(
        clientEmail: String,
        barberEmail: String,
        grade: Int,
        text: String,
        date: String
    ) {
        reviewDao.submitReview(Review(
            id = 0,
            clientEmail = clientEmail,
            barberEmail = barberEmail,
            grade = grade,
            text = text,
            date = date
        ))
    }

    suspend fun getClientReviewsForBarber(
        clientEmail: String,
        barberEmail: String
    ): List<Review> {
        return reviewDao.getClientReviewsForBarber(clientEmail, barberEmail)
    }

    suspend fun getBarberReviews(barberEmail: String): List<ExtendedReviewWithClient> {
        return reviewDao.getBarberReviews(barberEmail)
    }

}