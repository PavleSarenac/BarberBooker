package rs.ac.bg.etf.barberbooker.data.room.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import rs.ac.bg.etf.barberbooker.data.reservationStatuses
import rs.ac.bg.etf.barberbooker.data.room.entities.Reservation

@Dao
interface ReservationDao {

    @Insert
    suspend fun addNewReservation(reservation: Reservation)

    @Query("""
        UPDATE reservation 
        SET status = :doneStatus 
        WHERE date = :currentDate AND :currentTime >= endTime
    """)
    suspend fun updateReservationStatuses(
        currentDate: String,
        currentTime: String,
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
        WHERE clientEmail = :clientEmail AND date = :date AND startTime = :time AND status = :acceptedStatus
    """)
    suspend fun getClientReservationByDateTime(
        clientEmail: String,
        date: String,
        time: String,
        acceptedStatus: String = reservationStatuses[1]
    ): Reservation?

}