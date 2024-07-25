package rs.ac.bg.etf.barberbooker.data.room.entities.structures

data class ExtendedReservationWithClient(
    var reservationId: Long,
    var clientEmail: String,
    var barberEmail: String,
    var date: String,
    var startTime: String,
    var endTime: String,
    var status: String,

    var clientId: Long,
    var clientName: String,
    var clientSurname: String
)