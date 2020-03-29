package com.example.weatherapp.home.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherLocalData(@PrimaryKey(autoGenerate = true) var weatherID: Long = 0L,
                            @ColumnInfo(name = "location_id") var locationID: Long = 0L,
                            @ColumnInfo(name = "city_name") var cityName: String = "",
                            @ColumnInfo(name = "date") var date: String = "",
                            @ColumnInfo(name = "humidity") var humidity: Long = 0L,
                            @ColumnInfo(name = "pressure") var pressure: Long = 0L,
                            @ColumnInfo(name = "wind_speed") var windSpeed: Int = 0,
                            @ColumnInfo(name = "wind_direction") var windDirection: Long = 0L,
                            @ColumnInfo(name = "high")var high: Double = 0.0, @ColumnInfo(name = "low") var low: Double = 0.0,
                            @ColumnInfo(name = "description")var description: String = "",
                            @ColumnInfo(name = "description_id") var descriptionID: Int = 0) {}