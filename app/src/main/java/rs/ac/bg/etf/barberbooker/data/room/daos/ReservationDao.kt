package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.reservationStatuses
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedReservationWithBarber
import rs.ac.bg.etf.barberbooker.data.room.entities.structures.ExtendedReservationWithClient
import rs.ac.bg.etf.barberbooker.data.room.entities.tables.Reservation

@Dao
interface ReservationDao {

    @Insert
    suspend fun addNewReservation(reservation: Reservation)

    @Query("""
        UPDATE reservation 
        SET status = :doneStatus 
        WHERE date = :currentDate AND :currentTime >= endTime AND status = :acceptedStatus
    """)
    suspend fun updateReservationStatuses(
        currentDate: String,
        currentTime: String,
        acceptedStatus: String = reservationStatuses[1],
        doneStatus: String = reservationStatuses[3]
    )

    @Query("""
        SELECT * FROM reservation
        WHERE barberEmail = :barberEmail AND date = :date AND startTime = :time AND status = :acceptedStatus
    """)
    suspend fun getBarberReservationByDateTime(
        barberEmail: String,
        date: String,
        time: String,
        acceptedStatus: String = reservationStatuses[1]
    ): Reservation?

    @Query("""
        SELECT * FROM reservation
        WHERE clientEmail = :clientEmail AND date = :date AND startTime = :time AND status != :rejectedStatus
    """)
    suspend fun getClientReservationByDateTime(
        clientEmail: String,
        date: String,
        time: String,
        rejectedStatus: String = reservationStatuses[2]
    ): Reservation?

    @Query("""
        SELECT * FROM reservation
        WHERE clientEmail = :clientEmail AND barberEmail = :barberEmail 
        AND date = :date AND startTime = :time AND status = :rejectedStatus
    """)
    suspend fun getRejectedReservationRequest(
        clientEmail: String,
        barberEmail: String,
        date: String,
        time: String,
        rejectedStatus: String = reservationStatuses[2]
    ): Reservation?

    @Query("""
        SELECT 
            r.id AS reservationId,
            r.clientEmail,
            r.barberEmail,
            r.date,
            r.startTime,
            r.endTime,
            r.status,
            b.id AS barberId,
            b.barbershopName
        FROM reservation r
        INNER JOIN barber b ON r.barberEmail = b.email
        WHERE clientEmail = :clientEmail AND status = :pendingStatus
    """)
    suspend fun getClientPendingReservationRequests(
        clientEmail: String,
        pendingStatus: String = reservationStatuses[0]
    ): List<ExtendedReservationWithBarber>

    @Query("""
        SELECT 
            r.id AS reservationId,
            r.clientEmail,
            r.barberEmail,
            r.date,
            r.startTime,
            r.endTime,
            r.status,
            b.id AS barberId,
            b.barbershopName
        FROM reservation r
        INNER JOIN barber b ON r.barberEmail = b.email
        WHERE clientEmail = :clientEmail AND status = :acceptedStatus
    """)
    suspend fun getClientAppointments(
        clientEmail: String,
        acceptedStatus: String = reservationStatuses[1]
    ): List<ExtendedReservationWithBarber>

    @Query("""
        SELECT 
            r.id AS reservationId,
            r.clientEmail,
            r.barberEmail,
            r.date,
            r.startTime,
            r.endTime,
            r.status,
            b.id AS barberId,
            b.barbershopName
        FROM reservation r
        INNER JOIN barber b ON r.barberEmail = b.email
        WHERE clientEmail = :clientEmail AND status = :rejectedStatus
    """)
    suspend fun getClientRejections(
        clientEmail: String,
        rejectedStatus: String = reservationStatuses[2]
    ): List<ExtendedReservationWithBarber>

    @Query("""
        SELECT 
            r.id AS reservationId,
            r.clientEmail,
            r.barberEmail,
            r.date,
            r.startTime,
            r.endTime,
            r.status,
            b.id AS barberId,
            b.barbershopName
        FROM reservation r
        INNER JOIN barber b ON r.barberEmail = b.email
        WHERE clientEmail = :clientEmail AND status = :doneStatus
    """)
    suspend fun getClientArchive(
        clientEmail: String,
        doneStatus: String = reservationStatuses[3]
    ): List<ExtendedReservationWithBarber>

    @Query("""
        SELECT 
            r.id AS reservationId,
            r.clientEmail,
            r.barberEmail,
            r.date,
            r.startTime,
            r.endTime,
            r.status,
            c.id AS clientId,
            c.name AS clientName,
            c.surname AS clientSurname
        FROM reservation r
        INNER JOIN client c ON r.clientEmail = c.email
        WHERE barberEmail = :barberEmail AND status = :pendingStatus
    """)
    suspend fun getBarberPendingReservationRequests(
        barberEmail: String,
        pendingStatus: String = reservationStatuses[0]
    ): List<ExtendedReservationWithClient>

    @Query("""
        SELECT 
            r.id AS reservationId,
            r.clientEmail,
            r.barberEmail,
            r.date,
            r.startTime,
            r.endTime,
            r.status,
            c.id AS clientId,
            c.name AS clientName,
            c.surname AS clientSurname
        FROM reservation r
        INNER JOIN client c ON r.clientEmail = c.email
        WHERE barberEmail = :barberEmail AND status = :acceptedStatus
    """)
    suspend fun getBarberAppointments(
        barberEmail: String,
        acceptedStatus: String = reservationStatuses[1]
    ): List<ExtendedReservationWithClient>

    @Query("""
        SELECT 
            r.id AS reservationId,
            r.clientEmail,
            r.barberEmail,
            r.date,
            r.startTime,
            r.endTime,
            r.status,
            c.id AS clientId,
            c.name AS clientName,
            c.surname AS clientSurname
        FROM reservation r
        INNER JOIN client c ON r.clientEmail = c.email
        WHERE barberEmail = :barberEmail AND status = :doneStatus
    """)
    suspend fun getBarberArchive(
        barberEmail: String,
        doneStatus: String = reservationStatuses[3]
    ): List<ExtendedReservationWithClient>

    @Query("""
        SELECT 
            r.id AS reservationId,
            r.clientEmail,
            r.barberEmail,
            r.date,
            r.startTime,
            r.endTime,
            r.status,
            c.id AS clientId,
            c.name AS clientName,
            c.surname AS clientSurname
        FROM reservation r
        INNER JOIN client c ON r.clientEmail = c.email
        WHERE barberEmail = :barberEmail AND status = :rejectedStatus
    """)
    suspend fun getBarberRejections(
        barberEmail: String,
        rejectedStatus: String = reservationStatuses[2]
    ): List<ExtendedReservationWithClient>

    @Query("""
        UPDATE reservation
        SET status = :acceptedStatus
        WHERE id = :reservationId
    """)
    suspend fun acceptReservationRequest(
        reservationId: Long,
        acceptedStatus: String = reservationStatuses[1]
    )

    @Query("""
        UPDATE reservation
        SET status = :rejectedStatus
        WHERE id = :reservationId
    """)
    suspend fun rejectReservationRequest(
        reservationId: Long,
        rejectedStatus: String = reservationStatuses[2]
    )

}