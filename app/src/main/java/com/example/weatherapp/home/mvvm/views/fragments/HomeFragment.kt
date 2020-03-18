package com.example.weatherapp.home.mvvm.views.fragments

import androidx.databinding.ViewDataBinding
import com.example.weatherapp.R
import com.example.weatherapp.base.mvvm.views.BaseFragment

class HomeFragment<FragmentHomeBinding : ViewDataBinding>: BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }
}