package com.github.aptemkov.asteroidradar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.github.aptemkov.asteroidradar.database.AsteroidsRoomDatabase

class UpdateWorker(val context: Context, val workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val database = AsteroidsRoomDatabase.getDatabase(context)
        val repository = AsteroidsRepository(database)

        return try {
            repository.refreshAsteroids()
            Result.success()
        } catch (e: java.lang.Exception) {
            Result.failure()
        }

    }
}