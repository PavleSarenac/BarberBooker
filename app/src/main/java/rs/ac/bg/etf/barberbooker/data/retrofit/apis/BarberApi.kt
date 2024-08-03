package rs.ac.bg.etf.barberbooker.data.retrofit.apis

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.structures.ExtendedBarberWithAverageGrade
import rs.ac.bg.etf.barberbooker.data.retrofit.entities.tables.Barber

const val BARBER_URL = "http://192.168.0.31:8080/barber/"

interface BarberApi {

    @POST("addNewBarber")
    suspend fun addNewBarber(@Body barber: Barber)

    @GET("getBarberByEmail")
    suspend fun getBarberByEmail(@Query("email") email: String): Response<Barber>

    @GET("getBarberByEmailAndPassword")
    suspend fun getBarberByEmailAndPassword(
        @Query("email") email: String,
        @Query("password") hashedPassword: String
    ): Response<Barber>

    @GET("updateBarberProfile")
    suspend fun updateBarberProfile(
        @Query("email") email: String,
        @Query("barbershopName") barbershopName: String,
        @Query("price") price: Double,
        @Query("phone") phone: String,
        @Query("country") country: String,
        @Query("city") city: String,
        @Query("municipality") municipality: String,
        @Query("address") address: String,
        @Query("workingDays") workingDays: String,
        @Query("workingHours") workingHours: String
    )

    @GET("getSearchResults")
    suspend fun getSearchResults(@Query("query") query: String): List<ExtendedBarberWithAverageGrade>

}