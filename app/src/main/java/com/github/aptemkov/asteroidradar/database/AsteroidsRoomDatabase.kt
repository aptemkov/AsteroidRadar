package com.github.aptemkov.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.aptemkov.asteroidradar.Asteroid

@Database(entities = [Asteroid::class], version = 1, exportSchema = false)
abstract class AsteroidsRoomDatabase : RoomDatabase() {
    abstract fun asteroidsDao(): AsteroidsDao

    companion object {

        private var INSTANCE: AsteroidsRoomDatabase? = null

        fun getDatabase(context: Context): AsteroidsRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AsteroidsRoomDatabase::class.java,
                    "asteroids_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }


}
