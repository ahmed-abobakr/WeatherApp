package com.example.weatherapp.base.data

import android.util.Log
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface APIManager{


    fun mapException(throwable: Throwable): Throwable

    fun <T> create(cls: Class<T>):  T

    fun <T> execute(apiCall: () -> T): BaseResponseModel<T>
}

private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory()).build()

private const val BASE_URL = "https://api.openweathermap.org/data/2.5/forecast/"

private val httpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

private val okHttpClient = OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).build()

val retrofit: Retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

enum class Status{
    SUCCESS, API_EXCEPTION, AUTH_EXCEPTION, NETWORK_EXCEPTION, UNKONWN_EXCEPTION
}
