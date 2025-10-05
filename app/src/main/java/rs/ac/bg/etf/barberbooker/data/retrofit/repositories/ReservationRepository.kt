package rs.ac.bg.etf.barberbooker.data.retrofit.repositories

import rs.ac.bg.etf.barberbooker.data.retrofit.apis.ReservationApi
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedReservationWithBarber
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedReservationWithClient
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.tables.Reservation
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReservationRepository @Inject constructor(
    private val reservationApi: ReservationApi
) {
    suspend fun addNewReservation(reservation: Reservation) {
        reservationApi.addNewReservation(reservation)
    }

    suspend fun getAllValidTimeSlots(
        clientEmail: String,
        barberEmail: String,
        date: String
    ): List<String> {
        val response = reservationApi.getAllValidTimeSlots(clientEmail, barberEmail, date)
        return response.body() ?: listOf()
    }

    suspend fun updateReservationStatuses(currentDate: String, currentTime: String) {
        reservationApi.updateReservationStatuses(currentDate, currentTime)
    }

    suspend fun updatePendingRequests(currentDate: String, currentTime: String) {
        reservationApi.updatePendingRequests(currentDate, currentTime)
    }

    suspend fun getClientPendingReservationRequests(clientEmail: String): List<ExtendedReservationWithBarber> {
        return reservationApi.getClientPendingReservationRequests(clientEmail)
    }

    suspend fun getClientAppointments(clientEmail: String): List<ExtendedReservationWithBarber> {
        return reservationApi.getClientAppointments(clientEmail)
    }

    suspend fun getClientRejections(clientEmail: String): List<ExtendedReservationWithBarber> {
        return reservationApi.getClientRejections(clientEmail)
    }

    suspend fun getClientArchive(clientEmail: String): List<ExtendedReservationWithBarber> {
        return reservationApi.getClientArchive(clientEmail)
    }

    suspend fun getBarberPendingReservationRequests(barberEmail: String): List<ExtendedReservationWithClient> {
        return reservationApi.getBarberPendingReservationRequests(barberEmail)
    }

    suspend fun getBarberAppointments(barberEmail: String): List<ExtendedReservationWithClient> {
        return reservationApi.getBarberAppointments(barberEmail)
    }

    suspend fun getBarberArchive(barberEmail: String): List<ExtendedReservationWithClient> {
        return reservationApi.getBarberArchive(barberEmail)
    }

    suspend fun getBarberRejections(barberEmail: String): List<ExtendedReservationWithClient> {
        return reservationApi.getBarberRejections(barberEmail)
    }

    suspend fun getBarberConfirmations(barberEmail: String): List<ExtendedReservationWithClient> {
        return reservationApi.getBarberConfirmations(barberEmail)
    }

    suspend fun acceptReservationRequest(reservationId: Int) {
        reservationApi.acceptReservationRequest(reservationId)
    }

    suspend fun rejectReservationRequest(reservationId: Int) {
        reservationApi.rejectReservationRequest(reservationId)
    }

    suspend fun updateDoneReservationStatus(reservationId: Int, status: String) {
        reservationApi.updateDoneReservationStatus(reservationId, status)
    }

}