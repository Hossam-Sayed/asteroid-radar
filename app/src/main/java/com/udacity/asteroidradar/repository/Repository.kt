package com.udacity.asteroidradar.repository

import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Repository(private val database: AsteroidDatabase) {

    val pictureOfDay = database.pictureOfDayDao.getPictureOfDayFromDatabase()

    /**
     ** Updates the asteroids in the database from the API if the data are different
     **/
    suspend fun updateAsteroidsDatabase(startDate: String, endDate: String, apiKey: String) {
        withContext(Dispatchers.IO) {
            val asteroids = getAsteroidListFromApi(startDate, endDate, apiKey)
            database.asteroidDao.delete()
            database.asteroidDao.insertAll(asteroids)
        }
    }

    /**
     * Returns a ArrayList of asteroids from the API that meets the start and end dates
     * */
    private suspend fun getAsteroidListFromApi(
        startDate: String,
        endDate: String,
        apiKey: String
    ): ArrayList<Asteroid> {
        return parseAsteroidsJsonResult(
            JSONObject(Network.asteroids.getProperties(startDate, endDate, apiKey))
        )
    }

    /**
     * Checks if the new resource from the API is a picture (not a video)
     * If it is a picture, it checks if the picture from the API is different from the one in the database
     * If the pictures were different, it updates the picture of the day in the database with the one in the API
     * */
    suspend fun updatePictureOfDay(apiKey: String) {
        withContext(Dispatchers.IO) {
            val pictureOfDayFromApi: PictureOfDay = Network.asteroids.getPictureOfTheDay(apiKey)
            if (
                pictureOfDayFromApi.mediaType == "image"
                && pictureOfDay.value?.url != pictureOfDayFromApi.url
            ) {
                database.pictureOfDayDao.clear()
                database.pictureOfDayDao.insert(pictureOfDayFromApi)
            }
        }
    }

    /**
     * Returns today's date in the form of a String
     * */
    fun getStartDate(): String =
        SimpleDateFormat(
            Constants.API_QUERY_DATE_FORMAT,
            Locale.getDefault()
        ).format(Calendar.getInstance().time)

    /**
     * Returns the end of the week's date in the form of a String
     * */
    fun getEndDate(): String {
        val endDate = Calendar.getInstance()
        endDate.add(Calendar.DAY_OF_YEAR, Constants.DEFAULT_END_DATE_DAYS)
        return SimpleDateFormat(
            Constants.API_QUERY_DATE_FORMAT,
            Locale.getDefault()
        ).format(endDate.time)
    }

}