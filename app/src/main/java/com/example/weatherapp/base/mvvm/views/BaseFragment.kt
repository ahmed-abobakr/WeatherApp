package com.example.weatherapp.base.mvvm.views

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.example.weatherapp.base.mvvm.views.widgets.BlockLoadingView

open abstract class BaseFragment<T: ViewDataBinding>: BaseViewOnlyFragment<T>() {

    private lateinit var blockLoadingView: BlockLoadingView

    protected fun showBlockLoading(context: Context){
        blockLoadingView = BlockLoadingView(context)
        blockLoadingView.showBlockLoading()
    }

    protected fun hideBlockLoading(){
        blockLoadingView.hideBlockLoading()
    }
}