package com.example.weatherapp.home.mvvm.views.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.DEFAULT_CITY
import com.example.weatherapp.R
import com.example.weatherapp.base.mvvm.views.BaseFragment
import com.example.weatherapp.home.mvvm.factories.HomeViewModelFactory
import com.example.weatherapp.home.mvvm.view_models.HomeViewModel
import com.example.weatherapp.home.mvvm.views.adapters.WeatherAdapter
import com.example.weatherapp.home.mvvm.views.dialogs.ChooseCityDialogFragment
import kotlinx.android.synthetic.main.fragment_home.*
import android.content.DialogInterface

class HomeFragment<FragmentHomeBinding : ViewDataBinding>: BaseFragment<FragmentHomeBinding>(), DialogInterface {

    private lateinit var homeViewModel: HomeViewModel
    private  var city: String? = null

    override fun getLayoutID(): Int {
        return R.layout.fragment_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProviders.of(
            this,
            HomeViewModelFactory(requireNotNull(this.activity).application)
        ).get(HomeViewModel::class.java)
        homeViewModel.savedCity.observe(this, Observer { city ->
            if(city == null || city.isEmpty()){
                showChooseCityDialog()
            }else
                this.city = city
        })

        homeViewModel.weatherLiveData.observe(this, Observer {
            val adapter = WeatherAdapter(it)
            forecast_recycler.layoutManager = LinearLayoutManager(this.activity)
            forecast_recycler.adapter = adapter
            homeViewModel.isLoading(false)
        })

        homeViewModel.mIsLoading.observe(this, Observer { loading -> if(!loading) hideBlockLoading() })

        super.handleCloudError(homeViewModel)
    }

    private fun showChooseCityDialog() {
        val dialogFragment = ChooseCityDialogFragment()
        dialogFragment.show(requireNotNull(fragmentManager), "chooseCity")
       // fragmentManager?.let { it.executePendingTransactions() }
        dialogFragment.dialog?.setOnDismissListener { homeViewModel.getSavedCity() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_save -> {
                showBlockLoading(requireNotNull(activity))
                homeViewModel.isLoading(true)
                homeViewModel.getWeatherDataSync(false, city ?: DEFAULT_CITY)
                true
            }
            R.id.action_choose_city -> {
                showChooseCityDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun dismiss() {
        homeViewModel.getSavedCity()
    }

    override fun cancel() {
        homeViewModel.getSavedCity()
    }
}