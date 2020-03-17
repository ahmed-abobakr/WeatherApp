package com.example.weatherapp.base.mvvm.views

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding


open abstract class BaseViewOnlyActivity<T : ViewDataBinding>: AppCompatActivity() {

    @LayoutRes
    protected abstract fun getLayoutID():  Int

    private lateinit var mDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        mDataBinding = DataBindingUtil.setContentView(this, getLayoutID())
        mDataBinding.lifecycleOwner = this
    }

}