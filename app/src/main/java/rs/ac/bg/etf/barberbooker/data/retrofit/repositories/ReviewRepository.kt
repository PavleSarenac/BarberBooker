package rs.ac.bg.etf.barberbooker.data.retrofit.repositories

import rs.ac.bg.etf.barberbooker.data.retrofit.apis.ReviewApi
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedReviewWithBarber
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedReviewWithClient
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.tables.Review
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReviewRepository @Inject constructor(
    private val reviewApi: ReviewApi
) {

    suspend fun submitReview(
        clientEmail: String,
        barberEmail: String,
        grade: Int,
        text: String,
        date: String
    ) {
        reviewApi.submitReview(
            Review(
                id = 0,
                clientEmail = clientEmail,
                barberEmail = barberEmail,
                grade = grade,
                text = text,
                date = date
            )
        )
    }

    suspend fun getClientReviewsForBarber(
        clientEmail: String,
        barberEmail: String
    ): List<Review> {
        return reviewApi.getClientReviewsForBarber(clientEmail, barberEmail)
    }

    suspend fun getBarberReviews(barberEmail: String): List<ExtendedReviewWithClient> {
        return reviewApi.getBarberReviews(barberEmail)
    }

    suspend fun getClientReviews(clientEmail: String): List<ExtendedReviewWithBarber> {
        return reviewApi.getClientReviews(clientEmail)
    }

    suspend fun getBarberAverageGrade(barberEmail: String): Float {
        return reviewApi.getBarberAverageGrade(barberEmail)
    }

}