package rs.ac.bg.etf.barberbooker.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rs.ac.bg.etf.barberbooker.data.room.BarberBookerDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context) =
        BarberBookerDatabase.getDatabase(context)

    @Provides
    @Singleton
    fun providesBarberDao(database: BarberBookerDatabase) =
        database.barberDao()

    @Provides
    @Singleton
    fun providesClientDao(database: BarberBookerDatabase) =
        database.clientDao()

    @Provides
    @Singleton
    fun providesReservationDao(database: BarberBookerDatabase) =
        database.reservationDao()
}