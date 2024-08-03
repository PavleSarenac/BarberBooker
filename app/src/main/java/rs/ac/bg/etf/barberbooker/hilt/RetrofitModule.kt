package rs.ac.bg.etf.barberbooker.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.BARBER_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.BarberApi
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.CLIENT_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.ClientApi
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.FIREBASE_CLOUD_MESSAGING_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.FirebaseCloudMessagingApi
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.RESERVATION_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.REVIEW_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.ReservationApi
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.ReviewApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun providesBarberApi(): BarberApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BARBER_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(BarberApi::class.java)
    }

    @Singleton
    @Provides
    fun providesClientApi(): ClientApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(CLIENT_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ClientApi::class.java)
    }

    @Singleton
    @Provides
    fun providesReviewApi(): ReviewApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(REVIEW_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ReviewApi::class.java)
    }

    @Singleton
    @Provides
    fun providesReservationApi(): ReservationApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(RESERVATION_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ReservationApi::class.java)
    }

    @Singleton
    @Provides
    fun providesFirebaseCloudMessagingApi(): FirebaseCloudMessagingApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(FIREBASE_CLOUD_MESSAGING_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(FirebaseCloudMessagingApi::class.java)
    }

}