package com.example.weatherapp.home.data.local

import android.content.SharedPreferences
import com.example.weatherapp.base.data.PreferenceManager

class HomePreferenceManager(val preference: SharedPreferences): PreferenceManager {

    override fun saveCity(country: String) {
        preference.edit().putString("city", country).commit()
    }

    override fun getCity(): String {
        return preference.getString("city", "") ?: ""
    }
}