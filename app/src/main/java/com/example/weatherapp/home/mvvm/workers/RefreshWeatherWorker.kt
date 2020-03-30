package com.example.weatherapp.home.mvvm.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters


class RefreshWeatherWorker(context: Context, workerParameters: WorkerParameters, private val lambda: Unit):
                Worker(context, workerParameters) {

    override fun doWork(): Result {
        lambda
        return Result.success()
    }
}