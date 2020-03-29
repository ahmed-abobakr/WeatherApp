package com.example.weatherapp.home.mvvm.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.base.data.PreferenceManager
import com.example.weatherapp.home.data.local.HomePreferenceManager
import com.example.weatherapp.home.mvvm.view_models.ChooseCityViewModel
import java.lang.IllegalArgumentException

class ChooseCityViewModelFactory(private val context: Context): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChooseCityViewModel::class.java)){
            return ChooseCityViewModel(createPreferenceManager()) as T
        }
        throw IllegalArgumentException("Unknown View model class")
    }

    private fun createPreferenceManager(): PreferenceManager {
        return HomePreferenceManager(androidx.preference.PreferenceManager.getDefaultSharedPreferences(context))
    }

}