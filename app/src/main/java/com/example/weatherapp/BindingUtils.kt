package com.example.weatherapp

import android.widget.ImageView
import androidx.databinding.Bindable
import androidx.databinding.BindingAdapter
import com.example.weatherapp.home.data.models.WeatherLocalData

@BindingAdapter("weatherIcon")
fun ImageView.setWeatherIcon(weather: WeatherLocalData){
    setImageResource(when(weather.descriptionID){
        in 200..232 -> R.drawable.ic_storm
        in 300..321 -> R.drawable.ic_light_rain
        in 500..504 -> R.drawable.ic_rain
        511 -> R.drawable.ic_snow
        in 520..531 -> R.drawable.ic_rain
        in 600..622 -> R.drawable.ic_snow
        in 701..761 -> R.drawable.ic_fog
        781 -> R.drawable.ic_storm
        800 -> R.drawable.ic_clear
        801 -> R.drawable.ic_light_clouds
        in 802..804 -> R.drawable.ic_cloudy
        else -> -1
    })
}

@BindingAdapter("weatherArt")
fun ImageView.setWeatherArt(weather: WeatherLocalData){
    setImageResource(when(weather.descriptionID){
        in 200..232 -> R.drawable.art_storm
        in 300..321 -> R.drawable.art_light_rain
        in 500..504 -> R.drawable.art_rain
        511 -> R.drawable.art_snow
        in 520..531 -> R.drawable.art_rain
        in 600..622 -> R.drawable.art_snow
        in 701..761 -> R.drawable.art_fog
        781 -> R.drawable.art_storm
        800 -> R.drawable.art_clear
        801 -> R.drawable.art_light_clouds
        in 802..804 -> R.drawable.art_clouds
        else -> -1
    })
}