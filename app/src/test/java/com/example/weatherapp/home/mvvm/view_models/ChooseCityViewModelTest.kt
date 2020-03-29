package com.example.weatherapp.home.mvvm.view_models


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.base.data.PreferenceManager
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.*
import org.hamcrest.Matcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*

class ChooseCityViewModelTest{


    private lateinit var SUT: ChooseCityViewModel
    private lateinit var mPreferenceManager: PreferenceManager

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        mPreferenceManager = mock(PreferenceManager::class.java)
        SUT = ChooseCityViewModel(mPreferenceManager)
    }

    //saveCity with Cairo check if cityLiveData is Updated
    @Test
    fun saveCityWithCairoInput_cityLiveDataIsUpdated() {
        `when`(mPreferenceManager.getCity()).thenReturn("Cairo")
        SUT.cityLiveData.observeForever {}
        SUT.city = "Cairo"
        SUT.saveCity()
        assertThat(SUT.cityLiveData.value, `is`("Cairo"))
    }
    //saveCity with empty string check if cityLiveData is updated
    @Test
    fun saveCityWithEmptyInput_cityLiveDataIsUpdated() {
        `when`(mPreferenceManager.getCity()).thenReturn("")
        SUT.cityLiveData.observeForever {}
        SUT.city = ""
        SUT.saveCity()
        assertThat(SUT.cityLiveData.value, `is`(""))
    }



}