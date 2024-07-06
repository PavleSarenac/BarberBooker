package rs.ac.bg.etf.barberbooker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rs.ac.bg.etf.barberbooker.data.room.daos.BarberDao
import rs.ac.bg.etf.barberbooker.data.room.daos.ClientDao
import rs.ac.bg.etf.barberbooker.data.room.entites.Barber
import rs.ac.bg.etf.barberbooker.data.room.entites.Client

@Database(entities = [Client::class, Barber::class], version = 1, exportSchema = false)
abstract class BarberBookerDatabase : RoomDatabase() {
    abstract fun barberDao(): BarberDao

    abstract fun clientDao(): ClientDao

    companion object {
        private var INSTANCE: BarberBookerDatabase? = null

        fun getDatabase(context: Context): BarberBookerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BarberBookerDatabase::class.java,
                    "barber_booker_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                INSTANCE!!.openHelper.writableDatabase
                return@synchronized instance
            }
        }
    }
}