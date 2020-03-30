package com.example.weatherapp.home.data.models

import android.os.Parcel
import android.os.Parcelable
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
                            @ColumnInfo(name = "description_id") var descriptionID: Int = 0): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readLong(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(weatherID)
        parcel.writeLong(locationID)
        parcel.writeString(cityName)
        parcel.writeString(date)
        parcel.writeLong(humidity)
        parcel.writeLong(pressure)
        parcel.writeInt(windSpeed)
        parcel.writeLong(windDirection)
        parcel.writeDouble(high)
        parcel.writeDouble(low)
        parcel.writeString(description)
        parcel.writeInt(descriptionID)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WeatherLocalData> {
        override fun createFromParcel(parcel: Parcel): WeatherLocalData {
            return WeatherLocalData(parcel)
        }

        override fun newArray(size: Int): Array<WeatherLocalData?> {
            return arrayOfNulls(size)
        }
    }
}