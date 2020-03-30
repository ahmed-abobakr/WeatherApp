package com.example.weatherapp.base.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.weatherapp.home.data.models.WeatherData
import com.example.weatherapp.home.data.models.WeatherLocalData
import java.net.IDN

@Dao
interface WeatherDatabaseDao {

    @Insert
    fun insert(weatherData: WeatherLocalData)

    @Update
    fun update(weatherData: WeatherLocalData)

    @Query("Delete From weather_table")
    fun clear()

    @Query("SELECT * From weather_table")
    fun getAllWeatherData(): LiveData<List<WeatherLocalData>>

    @Query("SELECT * From weather_table WHERE weatherID = :weatherID")
    fun getWeatherDataByID(weatherID: Long): WeatherLocalData?
}