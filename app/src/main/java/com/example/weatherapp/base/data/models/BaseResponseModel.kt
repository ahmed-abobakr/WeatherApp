package com.example.weatherapp.base.data.models

import com.example.weatherapp.base.data.Status

data class BaseResponseModel<out T>(val status: Status, val data: T? = null, val message: String = "", val code: Int = -1)