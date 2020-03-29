package com.example.weatherapp.details.mvvm.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.base.mvvm.views.BaseViewOnlyFragment
import com.example.weatherapp.databinding.FragmentDetailsBinding
import com.example.weatherapp.home.data.models.WeatherLocalData

class DetailsFragment: BaseViewOnlyFragment<FragmentDetailsBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.fragment_details
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.weather = arguments?.get("weather") as WeatherLocalData
        mViewDataBinding.executePendingBindings()
    }
}