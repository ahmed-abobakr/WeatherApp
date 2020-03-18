package com.example.weatherapp.home.mvvm.views.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.databinding.ViewDataBinding
import com.example.weatherapp.R
import com.example.weatherapp.base.mvvm.views.BaseFragment

class HomeFragment<FragmentHomeBinding : ViewDataBinding>: BaseFragment<FragmentHomeBinding>() {

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_save -> true
            else -> super.onOptionsItemSelected(item)
        }

    }
}