package com.example.weatherapp.base.mvvm.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.base.data.DataManager
import com.example.weatherapp.base.data.Status
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.example.weatherapp.base.data.models.ErrorResponse
import com.squareup.moshi.Moshi
import retrofit2.HttpException
import java.io.IOException

open class BaseViewModel(open val mDataManager: DataManager): ViewModel() {


    protected val _mIsLoading = MutableLiveData<Boolean>()
    val mIsLoading: LiveData<Boolean>
        get() = _mIsLoading

    protected val _mCloudException = MutableLiveData<BaseResponseModel<ErrorResponse>>()
    val mCloudException: LiveData<BaseResponseModel<ErrorResponse>>
        get() = _mCloudException

    public fun isLoading(loading: Boolean){
        _mIsLoading.value = loading
    }


    protected fun handleCloudHttpErrors(throwable: Throwable){
        _mCloudException.value = when(throwable){
            is IOException -> BaseResponseModel(Status.NETWORK_EXCEPTION)
            is HttpException -> {
                val code = throwable.code()
                if(code == 401)
                    BaseResponseModel<ErrorResponse>(Status.AUTH_EXCEPTION)
                else{
                    val errorResponse = convertErrorBody(throwable)
                    if(errorResponse != null) {
                        BaseResponseModel<ErrorResponse>(Status.API_EXCEPTION, null, errorResponse.message, errorResponse.cod.toInt())
                    }else {
                        BaseResponseModel(Status.UNKONWN_EXCEPTION)
                    }
                }
            }
            else ->{
                throwable.printStackTrace()
                BaseResponseModel(Status.UNKONWN_EXCEPTION)
            }
        }
    }


    private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
        return try {
            throwable.response()?.errorBody()?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(ErrorResponse::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }

}