package com.example.weatherapp.base.mvvm.views

import androidx.databinding.ViewDataBinding
import com.example.weatherapp.base.mvvm.views.widgets.BlockLoadingView

open abstract class BaseActivity<T : ViewDataBinding>: BaseViewOnlyActivity<T>() {

    private lateinit var blockLoadingView: BlockLoadingView

    protected fun showBlockLoading(){
        blockLoadingView = BlockLoadingView(this)
        blockLoadingView.showBlockLoading()
    }

    protected fun hideBlockLoading(){
        blockLoadingView.hideBlockLoading()
    }

}