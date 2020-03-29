package com.example.weatherapp.base.mvvm.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open abstract class BaseViewOnlyFragment<T: ViewDataBinding> : Fragment() {

    protected lateinit var mViewDataBinding : T
    private lateinit var mView: View

    @LayoutRes
    protected abstract fun getLayoutID():  Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutID(), container, false)
        mView = mViewDataBinding.root
        mViewDataBinding.lifecycleOwner = this
        return mView
    }
}