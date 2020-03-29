package com.example.weatherapp.base.mvvm.views

import android.content.Context
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.example.weatherapp.base.data.Status
import com.example.weatherapp.base.mvvm.view_models.BaseViewModel
import com.example.weatherapp.base.mvvm.views.widgets.BlockLoadingView

open abstract class BaseFragment<T: ViewDataBinding>: BaseViewOnlyFragment<T>() {

    private var blockLoadingView: BlockLoadingView? = null

    protected fun showBlockLoading(context: Context){
        blockLoadingView = BlockLoadingView(context)
        blockLoadingView?.let { it.showBlockLoading() }
    }

    protected fun hideBlockLoading(){
        blockLoadingView?.let { it.hideBlockLoading() }
    }

    protected fun handleCloudError(viewModel: BaseViewModel){
        viewModel.mCloudException.observe(this, Observer {
            viewModel.isLoading(false)
            val message : String = when(it.status) {
                Status.NETWORK_EXCEPTION -> "please check your internet connection"
                Status.AUTH_EXCEPTION -> "UnAuthorized"
                Status.API_EXCEPTION -> it.message
                else -> "please try again later"
            }
            Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
        })
    }
}