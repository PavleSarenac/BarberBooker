package rs.ac.bg.etf.barberbooker.data.room.entities.structures

data class ExtendedReservationWithBarber(
    var reservationId: Long,
    var clientEmail: String,
    var barberEmail: String,
    var date: String,
    var startTime: String,
    var endTime: String,
    var status: String,

    var barberId: Long,
    var barbershopName: String
)