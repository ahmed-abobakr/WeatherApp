package com.example.weatherapp.base.mvvm.views.widgets

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog

class BlockLoadingView(private val context: Context) {

    private var mProgressDialog : MaterialDialog? = null

    fun showBlockLoading(){
        if(mProgressDialog == null){
            mProgressDialog = MaterialDialog(context)
        }
        mProgressDialog?.let {
            it.cancelable(false)
            it.show()
        }
    }

    fun hideBlockLoading(){
        mProgressDialog?.let { it.dismiss() }
    }
}


