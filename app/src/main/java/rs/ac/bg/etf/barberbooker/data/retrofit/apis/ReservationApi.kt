package rs.ac.bg.etf.barberbooker.data.retrofit.apis

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedReservationWithBarber
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedReservationWithClient
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.tables.Reservation

const val RESERVATION_URL = "http://192.168.0.34:8080/reservation/"

interface ReservationApi {

    @POST("addNewReservation")
    suspend fun addNewReservation(@Body reservation: Reservation)

    @GET("getBarberReservationByDateTime")
    suspend fun getBarberReservationByDateTime(
        @Query("barberEmail") barberEmail: String,
        @Query("date") date: String,
        @Query("time") time: String
    ): Response<Reservation>

    @GET("getClientReservationByDateTime")
    suspend fun getClientReservationByDateTime(
        @Query("clientEmail") clientEmail: String,
        @Query("date") date: String,
        @Query("time") time: String
    ): Response<Reservation>

    @GET("updateReservationStatuses")
    suspend fun updateReservationStatuses(
        @Query("currentDate") currentDate: String,
        @Query("currentTime") currentTime: String
    )

    @GET("updatePendingRequests")
    suspend fun updatePendingRequests(
        @Query("currentDate") currentDate: String,
        @Query("currentTime") currentTime: String
    )

    @GET("getRejectedReservationRequest")
    suspend fun getRejectedReservationRequest(
        @Query("clientEmail") clientEmail: String,
        @Query("barberEmail") barberEmail: String,
        @Query("date") date: String,
        @Query("time") time: String
    ): Response<Reservation>

    @GET("getClientPendingReservationRequests")
    suspend fun getClientPendingReservationRequests(
        @Query("clientEmail") clientEmail: String
    ): List<ExtendedReservationWithBarber>

    @GET("getClientAppointments")
    suspend fun getClientAppointments(
        @Query("clientEmail") clientEmail: String
    ): List<ExtendedReservationWithBarber>

    @GET("getClientRejections")
    suspend fun getClientRejections(
        @Query("clientEmail") clientEmail: String
    ): List<ExtendedReservationWithBarber>

    @GET("getClientArchive")
    suspend fun getClientArchive(
        @Query("clientEmail") clientEmail: String
    ): List<ExtendedReservationWithBarber>

    @GET("getBarberPendingReservationRequests")
    suspend fun getBarberPendingReservationRequests(
        @Query("barberEmail") barberEmail: String
    ): List<ExtendedReservationWithClient>

    @GET("getBarberAppointments")
    suspend fun getBarberAppointments(
        @Query("barberEmail") barberEmail: String
    ): List<ExtendedReservationWithClient>

    @GET("getBarberArchive")
    suspend fun getBarberArchive(
        @Query("barberEmail") barberEmail: String
    ): List<ExtendedReservationWithClient>

    @GET("getBarberRejections")
    suspend fun getBarberRejections(
        @Query("barberEmail") barberEmail: String
    ): List<ExtendedReservationWithClient>

    @GET("acceptReservationRequest")
    suspend fun acceptReservationRequest(
        @Query("reservationId") reservationId: Int
    )

    @GET("rejectReservationRequest")
    suspend fun rejectReservationRequest(
        @Query("reservationId") reservationId: Int
    )

}