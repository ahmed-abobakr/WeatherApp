package com.example.weatherapp.home.mvvm.view_models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.weatherapp.TestCoroutineRule
import com.example.weatherapp.base.data.*
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.example.weatherapp.getTimeFormatted
import com.example.weatherapp.home.data.HomeDataManager
import com.example.weatherapp.home.data.cloud.HomeServiceImpl
import com.example.weatherapp.home.data.models.*
import com.example.weatherapp.mapWeatherDataToWeatherLocal
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mockito.*

class HomeViewModelTest{

    private lateinit var  SUT: HomeViewModel
    private lateinit var cloudTD: HomeCloudTD
    private lateinit var dbManagerTD: HomeDBManagerTD
    private lateinit var preferenceManagerMock: PreferenceManager
    private lateinit var appDataManager: HomeDataManager

    companion object {
        val DETAILS_WEATHER = Details("", "", 0)
        val TEMP_WEATHER = Temp(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val WEATHER_WEATHER = Weather(0L, 0L, TEMP_WEATHER, 0L, 0L, listOf(DETAILS_WEATHER), 0L, 0L, 0, 0.0)
        val COORD = mock(Coord::class.java)
        val CITY = City(0L, "", "", COORD)
        val WEATHER = WeatherData(CITY, 0, "", 0.0, listOf(WEATHER_WEATHER))
        val WEATHER_LOCAL = WeatherLocalData(0L, 0, "", getTimeFormatted(0))
        val WEATHER_LOCAL_LIST = listOf(mapWeatherDataToWeatherLocal(CITY, WEATHER_WEATHER, getTimeFormatted(0)))
        val RESPONSE_SUCCESS = BaseResponseModel(Status.SUCCESS, CompletableDeferred(WEATHER))
        val RESPONSE_NETWORK_FAILURE: BaseResponseModel<Deferred<WeatherData>> = BaseResponseModel(Status.NETWORK_EXCEPTION)
        val RESPONSE_AUTH_FAILURE = BaseResponseModel<Deferred<WeatherData>>(Status.AUTH_EXCEPTION)
        val RESPONSE_API_FAILURE = BaseResponseModel<Deferred<WeatherData>>(Status.API_EXCEPTION)
        val RESPONSE_OTHER_FAILURE = BaseResponseModel<Deferred<WeatherData>>(Status.UNKONWN_EXCEPTION)

    }

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val  testCoroutineRule = TestCoroutineRule()

    @Before
    fun setUp() {
        cloudTD = HomeCloudTD()
        dbManagerTD = HomeDBManagerTD()
        preferenceManagerMock = mock(PreferenceManager::class.java)
        appDataManager = HomeDataManager(cloudTD, dbManagerTD, preferenceManagerMock)
        SUT = HomeViewModel(appDataManager)
    }

    //check If There is a Country saved in the preference if no update countryPersistenceLiveData
    @Test
    fun getSavedCity_emptyCountry_cityPersistenceDataIsEmpty() {
        `when`(preferenceManagerMock.getCity()).thenReturn("")
        SUT.savedCity.observeForever {}
        SUT.getSavedCity()
        assertThat(SUT.savedCity.value, `is`(""))
    }

    @Test
    fun getSavedCity_cityReturned_cityPersistenceDataIsReturned() {
        `when`(preferenceManagerMock.getCity()).thenReturn("cairo")
        SUT.savedCity.observeForever {}
        SUT.getSavedCity()
        assertThat(SUT.savedCity.value, `is`("cairo"))
    }
    //check If There is a Country saved in the preference if yes update countryPersistenceLiveData
    @Test
    fun saveCity_success_cityPersistenceDataIsUpdated() {
        `when`(preferenceManagerMock.getCity()).thenReturn("Cairo")
        SUT.savedCity.observeForever {}
        SUT.saveCity("Cairo")
        assertThat(SUT.savedCity.value, `is`("Cairo"))
    }

    //getWeatherDataSync must be tested with Couroutines
    //getWeatherDataSync with refresh false from cloud with success check if weatherData model is sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshFalse_success_dbManagerIsSaved() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.SUCCESS
            SUT.getWeatherDataSync(false, "Cairo")
            assertThat(dbManagerTD.weatherData, `is`(WEATHER_LOCAL))
        }
    }
    //getWeatherDataSync with refresh false from cloud with network fail check if weatherData model is not sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshFalse_networkFail_dbManagerIsNotSaved() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.NETWORK_EXCEPTION
            SUT.getWeatherDataSync(false, "Cairo")
            assertNull(dbManagerTD.weatherData)
        }
    }
    //getWeatherDataSync with refresh false from cloud with auth fail return check if weatherData model is not sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshFalse_authFail_dbManagerIsNotSaved() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.AUTH_EXCEPTION
            SUT.getWeatherDataSync(false, "Cairo")
            assertNull(dbManagerTD.weatherData)
        }
    }
    //getWeatherDataSync with refresh false from cloud with api fail return check if weatherData model is not sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshFalse_apiFail_dbManagerIsNotSaved() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.API_EXCEPTION
            SUT.getWeatherDataSync(false, "Cairo")
            assertNull(dbManagerTD.weatherData)
        }
    }
    //getWeatherDataSync with refresh false from cloud with other fail return check if weatherData model is not sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshFalse_otherFail_dbManagerIsNotSaved() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.UNKONWN_EXCEPTION
            SUT.getWeatherDataSync(false, "Cairo")
            assertNull(dbManagerTD.weatherData)
        }
    }
    //getWeatherDataSync with refresh false from cloud with success check if cloudException is not updated
    @Test
    fun `getWeatherDataSyncWithRefresh false_success_cloudExceptionLiveDataIsNotUpdated`() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.SUCCESS
            SUT.getWeatherDataSync(false, "Cairo")
            assertNull(SUT.mCloudException.value)
        }
    }
    //getWeatherDataSync with refresh false from cloud with network fail check if cloudException is updated
    //getWeatherDataSync with refresh false from cloud with auth fail check if cloudException is updated
    //getWeatherDataSync with refresh false from cloud with api fail check if cloudException is updated
    //getWeatherDataSync with refresh false from cloud with other fail check if cloudException is updated
    //getWeatherDataSync with refresh true from cloud with success check if weatherData model is sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshTrue_success_dbManagerIsUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.SUCCESS
            SUT.getWeatherDataSync(true, "Cairo")
            assertThat(dbManagerTD.weatherUpdated, `is`(WEATHER_LOCAL))
        }
    }
    //getWeatherDataSync with refresh true from cloud with network fail return check if weatherData model is not sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshTrue_networkFail_dbManagerIsNotUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.NETWORK_EXCEPTION
            SUT.getWeatherDataSync(true, "Cairo")
            assertNull(dbManagerTD.weatherUpdated)
        }
    }
    //getWeatherDataSync with refresh true from cloud with auth fail return check if weatherData model is not sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshTrue_authFail_dbManagerIsNotUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.AUTH_EXCEPTION
            SUT.getWeatherDataSync(true, "Cairo")
            assertNull(dbManagerTD.weatherUpdated)
        }
    }
    //getWeatherDataSync with refresh true from cloud with api fail return check if weatherData model is not sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshTrue_apiFail_dbManagerIsNotUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.API_EXCEPTION
            SUT.getWeatherDataSync(true, "Cairo")
            assertNull(dbManagerTD.weatherUpdated)
        }
    }
    //getWeatherDataSync with refresh true from cloud with other fail check if weatherData model is not sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshTrue_otherFail_dbManagerIsNotUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.UNKONWN_EXCEPTION
            SUT.getWeatherDataSync(true, "Cairo")
            assertNull(dbManagerTD.weatherUpdated)
        }
    }
    //getWeatherDataSync with refresh true from cloud with success check if cloudException is not updated
    //getWeatherDataSync with refresh true from cloud with network fail check if cloudException is updated
    //getWeatherDataSync with refresh true from cloud with auth fail check if cloudException is updated
    //getWeatherDataSync with refresh true from cloud with api fail check if cloudException is updated
    //getWeatherDataSync with refresh true from cloud with other fail check if cloudException is updated
    //working with database will use Couroutines and suspend functions
    //saveWeatherData  return check  if weatherLiveDataLiveData is updated
    @Test
    fun saveWeatherData_weatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest{
         //   `when`(mapWeatherDataToWeatherLocal(CITY, WEATHER_WEATHER)).thenReturn(WEATHER_LOCAL)
            SUT.saveWeatherData(WEATHER)
            assertThat(SUT.weatherLiveData.value, `is`(WEATHER_LOCAL_LIST))
        }
    }
    //updateWeatherData return check  if weatherLiveDataLiveData is updated
    @Test
    fun updateWeatherData_weatherDataIsUpdated() {
        testCoroutineRule.runBlockingTest {
            SUT.updateWeatherData(WEATHER)
            assertThat(SUT.weatherLiveData.value, `is`(WEATHER_LOCAL_LIST))
        }
    }
    //deleteWeatherData to check if data is deleted
    @Test
    fun deleteWeatherData_weatherDataIsDeleted() {
        testCoroutineRule.runBlockingTest {
            SUT.deleteWeatherData()
            assertNull(dbManagerTD.getWeather().value)
        }
    }
    //---------------------------------------------------------------------
    //-----------------Helper Classes--------------------------------------


    private class HomeCloudTD: HomeServiceImpl {

        var status: Status? = null
        var city: String? = null



        override fun getWeatherForCity(city: String):BaseResponseModel<Deferred<WeatherData>> {
            this.city = city
            return when(status) {
                Status.SUCCESS -> RESPONSE_SUCCESS
                Status.NETWORK_EXCEPTION -> RESPONSE_NETWORK_FAILURE
                Status.AUTH_EXCEPTION -> RESPONSE_AUTH_FAILURE
                Status.API_EXCEPTION -> RESPONSE_API_FAILURE
                else -> RESPONSE_OTHER_FAILURE
            }
        }

        override fun mapException(throwable: Throwable): Throwable {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun <T> create(cls: Class<T>): T {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun <T> execute(apiCall: () -> T): BaseResponseModel<T> {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    private class HomeDBManagerTD: DbManager{
        var isDataDeleted: Boolean = false
        var weatherUpdated: WeatherLocalData? = null
        var weatherData: WeatherLocalData? = null

        override suspend fun saveWeather(weather: WeatherLocalData) {
            this.weatherData = weather
            this.weatherUpdated = weather
        }

        override suspend fun updateWeather(weather: WeatherLocalData) {
            weatherUpdated = weather
        }

        override fun getWeather(): LiveData<List<WeatherLocalData>> {
            return if(!isDataDeleted) {
                val weatherLiveData = MutableLiveData<List<WeatherLocalData>>()
                weatherLiveData.value = WEATHER_LOCAL_LIST
                return weatherLiveData
            }else
                MutableLiveData()
        }

        override suspend fun deleteWeather(weather: WeatherLocalData) {
            isDataDeleted = true
        }
    }
}