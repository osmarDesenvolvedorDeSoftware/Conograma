package com.osmardev.meucronograma.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.osmardev.meucronograma.model.DiarioEntry

@Database(entities = [DiarioEntry::class], version = 1, exportSchema = false)
abstract class DiarioDatabase : RoomDatabase() {
    abstract fun diarioDao(): DiarioDao

    companion object {
        @Volatile
        private var INSTANCE: DiarioDatabase? = null

        fun getDatabase(context: Context): DiarioDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DiarioDatabase::class.java,
                    "diario_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
