package rs.ac.bg.etf.barberbooker.data.room.entities.structures

data class ExtendedReviewWithBarber(
    var reviewId: Long,
    var clientEmail: String,
    var barberEmail: String,
    var grade: Int,
    var text: String,
    var date: String,

    var barberId: Long,
    var barbershopName: String
)