package com.example.weatherapp.home.mvvm.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters

class WeatherWorkerFactory(private val lambda: Unit): WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return RefreshWeatherWorker(appContext, workerParameters, lambda)
    }
}