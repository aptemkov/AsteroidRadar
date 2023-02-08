package com.github.aptemkov.asteroidradar

import android.app.Application
import androidx.work.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class AsteroidApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        callListUpdate()
    }

    private fun callListUpdate() {
        CoroutineScope(Dispatchers.Default).launch {
            setupRequest()
        }

    }

    private fun setupRequest() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .apply {
                setRequiresDeviceIdle(true)
            }.build()

        val repeatingRequest = PeriodicWorkRequestBuilder<UpdateWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "UpdateWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }

}