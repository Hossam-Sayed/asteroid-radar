package com.udacity.asteroidradar.database

import android.content.Context
import androidx.room.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay

@Database(entities = [Asteroid::class, PictureOfDay::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
    abstract val pictureOfDayDao: PictureOfDayDao

    companion object {
        @Volatile
        private lateinit var INSTANCE: AsteroidDatabase

        fun getInstance(context: Context): AsteroidDatabase {
            synchronized(AsteroidDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidDatabase::class.java,
                        "asteroids_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}
