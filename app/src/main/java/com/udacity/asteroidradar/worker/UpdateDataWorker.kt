package com.udacity.asteroidradar.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.BuildConfig.API_KEY
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.repository.Repository
import java.lang.Exception

class UpdateDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidDatabase.getInstance(applicationContext)
        val repository = Repository(database)
        return try {
            repository.updateAsteroidsDatabase(repository.getStartDate(), repository.getEndDate(), API_KEY)
            repository.updatePictureOfDay(API_KEY)
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}