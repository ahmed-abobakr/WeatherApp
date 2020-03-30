package com.example.weatherapp.home.mvvm.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager.*
import com.example.weatherapp.base.data.DbManager
import com.example.weatherapp.base.data.PreferenceManager
import com.example.weatherapp.base.data.WeatherDatabase
import com.example.weatherapp.home.data.HomeDataManager
import com.example.weatherapp.home.data.cloud.HomeCloud
import com.example.weatherapp.home.data.cloud.HomeServiceImpl
import com.example.weatherapp.home.data.local.HomeDBManager
import com.example.weatherapp.home.data.local.HomePreferenceManager
import com.example.weatherapp.home.mvvm.view_models.HomeViewModel
import java.lang.IllegalArgumentException

class HomeViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(HomeViewModel::class.java)){
            return HomeViewModel(HomeDataManager(createApiManager(), createDbManager(), createPreferenceManager())) as T
        }
        throw IllegalArgumentException("Unknown View model class")
    }

    private fun createApiManager(): HomeServiceImpl{
        return HomeCloud()
    }

    private fun createDbManager(): DbManager{
        return HomeDBManager(WeatherDatabase.getInstance(context).weatherDatabaseDao)
    }

    private fun createPreferenceManager(): PreferenceManager{
        return HomePreferenceManager(getDefaultSharedPreferences(context))
    }
}