package rs.ac.bg.etf.barberbooker.data.room.entities.structures

data class ExtendedReviewWithClient(
    var reviewId: Long,
    var clientEmail: String,
    var barberEmail: String,
    var grade: Int,
    var text: String,
    var date: String,

    var clientId: Long,
    var clientName: String,
    var clientSurname: String
)