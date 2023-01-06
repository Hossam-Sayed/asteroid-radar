package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidDatabase.Companion.getInstance
import com.udacity.asteroidradar.repository.Repository
import kotlinx.coroutines.launch
import com.udacity.asteroidradar.BuildConfig.API_KEY

enum class Filter { TODAY, WEEK, SAVED }

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToDetailScreen = MutableLiveData<Asteroid>()

    val navigateToDetailScreen: LiveData<Asteroid>
        get() = _navigateToDetailScreen

    private val database = getInstance(application)
    private val repository = Repository(database)

    val pictureOfDay = repository.pictureOfDay
    var asteroidsList: LiveData<List<Asteroid>>

    init {
        updateApiData()
        asteroidsList = database.asteroidDao.getAsteroids()
    }

    /**
     * Calls [updateAsteroidList] to update the filter
     * */
    fun updateFilter(filter: Filter) {
        updateAsteroidList(filter)
    }

    /**
     * Updates the filter
     * */
    private fun updateAsteroidList(filter: Filter) {
        viewModelScope.launch {
            val startDate = repository.getStartDate()
            try {
                when (filter) {
                    Filter.TODAY -> repository.updateAsteroidsDatabase(
                        startDate,
                        startDate,
                        API_KEY
                    )
                    Filter.WEEK -> repository.updateAsteroidsDatabase(
                        startDate,
                        repository.getEndDate(),
                        API_KEY
                    )
                    else -> repository.updateAsteroidsDatabase(
                        startDate,
                        repository.getEndDate(),
                        API_KEY
                    )
                }
            } catch (e: Exception) {
                Toast.makeText(
                    getApplication(),
                    "Failed to load asteroids. Check your internet connection and try again",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }


    /**
     * Setting [_navigateToDetailScreen] to an [asteroid] will alert the observer and trigger navigation.
     * @param [asteroid] is the clicked asteroid item in the RecyclerView
     * */
    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToDetailScreen.value = asteroid
    }

    /**
     * Called to indicate that navigation has completed
     * */
    fun displayAsteroidDetailsComplete() {
        _navigateToDetailScreen.value = null
    }

    /**
     * Calls the function in the [Repository] to update the data in the database from the API
     * */
    private fun updateApiData() {
        Log.i("MyLogs", "Entered updateApiData()")
        viewModelScope.launch {
            try {
                repository.updateAsteroidsDatabase(
                    repository.getStartDate(),
                    repository.getEndDate(),
                    API_KEY
                )
            } catch (e: Exception) {
                Log.i("MyLog", "Couldn't get the asteroids")
            }
            try {
                repository.updatePictureOfDay(API_KEY)
            } catch (e: Exception) {
                Log.i("MyLog", "Couldn't get the picture of the day")
            }
        }
    }
}