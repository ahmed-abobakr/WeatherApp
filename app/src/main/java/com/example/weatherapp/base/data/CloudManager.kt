package com.example.weatherapp.base.data

import android.accounts.NetworkErrorException
import com.example.weatherapp.base.exceptions.ApiException
import com.example.weatherapp.base.exceptions.AuthException
import com.example.weatherapp.base.exceptions.UnknownException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class  CloudManager: APIManager {

    override fun mapException(throwable: Throwable): Throwable {
        if(throwable is HttpException){
            val response = throwable.response()
            return try {
                val errorBodyString = response?.errorBody()?.string()
                if(throwable.code() == 401) AuthException(throwable)
                else{
                    if(errorBodyString != null){
                        ApiException(errorBodyString)
                    }else {
                        UnknownException(throwable)
                    }
                }
            }catch (exception: Exception){
                UnknownException(throwable)
            }
        }else if(throwable is IOException ||
                throwable is SocketException ||
                throwable is SocketTimeoutException ||
                throwable is UnknownHostException ||
                throwable is NetworkErrorException){
            return NetworkErrorException(throwable)
        }else {
            return UnknownException(throwable)
        }
    }

    override fun <T> execute(cls: Class<T>): T {
        return retrofit.create(cls)
    }

}