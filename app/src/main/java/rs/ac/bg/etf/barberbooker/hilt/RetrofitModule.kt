package rs.ac.bg.etf.barberbooker.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.BARBER_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.BarberApi
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.CLIENT_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.ClientApi
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.JWT_AUTHENTICATION_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.JwtAuthenticationApi
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.NOTIFICATION_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.NotificationApi
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.RESERVATION_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.REVIEW_URL
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.ReservationApi
import rs.ac.bg.etf.barberbooker.data.retrofit.apis.ReviewApi
import rs.ac.bg.etf.barberbooker.data.retrofit.utils.JwtAuthenticationUtils
import rs.ac.bg.etf.barberbooker.data.retrofit.utils.JwtAuthenticator
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun providesBarberApi(
        jwtAuthenticator: JwtAuthenticator
    ): BarberApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val jwtAuthenticationInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val token = JwtAuthenticationUtils.getJwtAccessToken()
            val requestBuilder = originalRequest.newBuilder()
            if (token.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(jwtAuthenticationInterceptor)
            authenticator(jwtAuthenticator)
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
    fun providesClientApi(
        jwtAuthenticator: JwtAuthenticator
    ): ClientApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val jwtAuthenticationInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val token = JwtAuthenticationUtils.getJwtAccessToken()
            val requestBuilder = originalRequest.newBuilder()
            if (token.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(jwtAuthenticationInterceptor)
            authenticator(jwtAuthenticator)
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
    fun providesReviewApi(
        jwtAuthenticator: JwtAuthenticator
    ): ReviewApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val jwtAuthenticationInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val token = JwtAuthenticationUtils.getJwtAccessToken()
            val requestBuilder = originalRequest.newBuilder()
            if (token.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(jwtAuthenticationInterceptor)
            authenticator(jwtAuthenticator)
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
    fun providesReservationApi(
        jwtAuthenticator: JwtAuthenticator
    ): ReservationApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val jwtAuthenticationInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val token = JwtAuthenticationUtils.getJwtAccessToken()
            val requestBuilder = originalRequest.newBuilder()
            if (token.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(jwtAuthenticationInterceptor)
            authenticator(jwtAuthenticator)
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
    fun providesNotificationApi(
        jwtAuthenticator: JwtAuthenticator
    ): NotificationApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val jwtAuthenticationInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val token = JwtAuthenticationUtils.getJwtAccessToken()
            val requestBuilder = originalRequest.newBuilder()
            if (token.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(jwtAuthenticationInterceptor)
            authenticator(jwtAuthenticator)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(NOTIFICATION_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(NotificationApi::class.java)
    }

    @Singleton
    @Provides
    fun providesJwtAuthenticationApi(): JwtAuthenticationApi {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val jwtAuthenticationInterceptor = Interceptor { chain ->
            val originalRequest = chain.request()
            val token = JwtAuthenticationUtils.getJwtAccessToken()
            val requestBuilder = originalRequest.newBuilder()
            if (token.isNotEmpty()) {
                requestBuilder.addHeader("Authorization", "Bearer $token")
            }
            chain.proceed(requestBuilder.build())
        }

        val okHttpClient = OkHttpClient.Builder().apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(jwtAuthenticationInterceptor)
        }.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(JWT_AUTHENTICATION_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(JwtAuthenticationApi::class.java)
    }
}