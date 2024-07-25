package rs.ac.bg.etf.barberbooker.data.room.repositories

import rs.ac.bg.etf.barberbooker.data.room.daos.ReservationDao
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedReservationWithBarber
import rs.ac.bg.etf.barberbooker.data.room.entities.tables.Reservation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationRepository @Inject constructor(
    private val reservationDao: ReservationDao
) {

    suspend fun addNewReservation(reservation: Reservation) {
        reservationDao.addNewReservation(reservation)
    }

    suspend fun getBarberReservationByDateTime(barberEmail: String, date: String, time: String): Reservation? {
        return reservationDao.getBarberReservationByDateTime(barberEmail, date, time)
    }

    suspend fun getClientReservationByDateTime(clientEmail: String, date: String, time: String): Reservation? {
        return reservationDao.getClientReservationByDateTime(clientEmail, date, time)
    }

    suspend fun updateReservationStatuses(currentDate: String, currentTime: String) {
        reservationDao.updateReservationStatuses(currentDate, currentTime)
    }

    suspend fun getRejectedReservationRequest(
        clientEmail: String,
        barberEmail: String,
        date: String,
        time: String
    ): Reservation? {
        return reservationDao.getRejectedReservationRequest(clientEmail, barberEmail, date, time)
    }

    suspend fun getClientPendingReservationRequests(clientEmail: String): List<ExtendedReservationWithBarber> {
        return reservationDao.getClientPendingReservationRequests(clientEmail)
    }

}