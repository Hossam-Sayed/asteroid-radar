package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay


@Dao
interface AsteroidDao {

    /**
     * Returns all the asteroids in the [AsteroidDatabase] sorted by approach date in ascending order
     */
    @Query("SELECT * FROM asteroid ORDER BY closeApproachDate ASC")
    fun getAsteroids(): LiveData<List<Asteroid>>

    /**
     * Inserts new Asteroids to the database
     * @param [asteroids] is a list of [Asteroid] that will be added to the [AsteroidDatabase]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: ArrayList<Asteroid>)

    /**
     * Deletes all values from the table.
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM asteroid")
    suspend fun delete()
}

@Dao
interface PictureOfDayDao {

    @Query("SELECT * FROM PictureOfDay")
    fun getPictureOfDayFromDatabase(): LiveData<PictureOfDay>

    /**
     * Deletes all values from the table.
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM PictureOfDay")
    suspend fun clear()

    /**
     * Inserts new Picture to the database
     * @param [pictureOfDay] is the [PictureOfDay] from the API
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pictureOfDay: PictureOfDay)
}