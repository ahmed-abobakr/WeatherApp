package com.example.weatherapp.home.mvvm.view_models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.weatherapp.TestCoroutineRule
import com.example.weatherapp.base.data.*
import com.example.weatherapp.base.data.models.BaseResponseModel
import com.example.weatherapp.home.data.HomeDataManager
import com.example.weatherapp.home.data.cloud.HomeServiceImpl
import com.example.weatherapp.home.data.models.WeatherData
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
        val WEATHER = mock(WeatherData::class.java)
        val RESPONSE_SUCCESS = BaseResponseModel(Status.SUCCESS, WEATHER)
        val RESPONSE_NETWORK_FAILURE = BaseResponseModel<WeatherData>(Status.NETWORK_EXCEPTION)
        val RESPONSE_AUTH_FAILURE = BaseResponseModel<WeatherData>(Status.AUTH_EXCEPTION)
        val RESPONSE_API_FAILURE = BaseResponseModel<WeatherData>(Status.API_EXCEPTION)
        val RESPONSE_OTHER_FAILURE = BaseResponseModel<WeatherData>(Status.UNKONWN_EXCEPTION)

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
    //check If There is a Country saved in the preference if no check country name not sent to cloud manager
    @Test
    fun getSavedCity_emptyCitySaved_cityNotSentToCloudManager() {
        `when`(preferenceManagerMock.getCity()).thenReturn("")
        SUT.getSavedCity()
        assertNull(cloudTD.city)
    }
    //check If There is a Country saved in the preference if yes send the country name to the cloud manger
    @Test
    fun getSavedCity_citySaved_citySentToCloudManager() {
        `when`(preferenceManagerMock.getCity()).thenReturn("Cairo")
        SUT.getSavedCity()
        assertThat(cloudTD.city, `is`("Cairo"))
    }
    //getWeatherDataSync must be tested with Couroutines
    //getWeatherDataSync with refresh false from cloud with success return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshFalse_success_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest{
            cloudTD.status = Status.SUCCESS
            SUT.getWeatherDataSync(false, "Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_SUCCESS))
        }
    }
    //getWeatherDataSync with refresh false from cloud with network fail return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshFalse_networkFail_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.NETWORK_EXCEPTION
            SUT.getWeatherDataSync(false, "Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_NETWORK_FAILURE))
        }
    }
    //getWeatherDataSync with refresh false from cloud with auth fail return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshFalse_authFail_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.AUTH_EXCEPTION
            SUT.getWeatherDataSync(false, "Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_AUTH_FAILURE))
        }
    }
    //getWeatherDataSync with refresh false from cloud with api fail return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshFalse_apiFail_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest{
            cloudTD.status = Status.API_EXCEPTION
            SUT.getWeatherDataSync(false, "Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_API_FAILURE))
        }
    }
    //getWeatherDataSync with refresh false from cloud with other fail return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshFalse_otherFail_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.UNKONWN_EXCEPTION
            SUT.getWeatherDataSync(false,"Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_OTHER_FAILURE))
        }
    }
    //getWeatherDataSync with refresh false from cloud with success check if weatherData model is sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshFalse_success_dbManagerIsSaved() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.SUCCESS
            SUT.getWeatherDataSync(false, "Cairo")
            assertThat(dbManagerTD.weatherData, `is`(WEATHER))
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
    //getWeatherDataSync with refresh true from cloud with success return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshTrue_success_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest{
            cloudTD.status = Status.SUCCESS
            SUT.getWeatherDataSync(true, "Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_SUCCESS))
        }
    }
    //getWeatherDataSync with refresh true from cloud with network fail return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshTrue_networkFail_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.NETWORK_EXCEPTION
            SUT.getWeatherDataSync(true, "Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_NETWORK_FAILURE))
        }
    }
    //getWeatherDataSync with refresh true from cloud with auth fail return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshTrue_authFail_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.AUTH_EXCEPTION
            SUT.getWeatherDataSync(true, "Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_AUTH_FAILURE))
        }
    }
    //getWeatherDataSync with refresh true from cloud with api fail return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshTrue_apiFail_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest{
            cloudTD.status = Status.API_EXCEPTION
            SUT.getWeatherDataSync(true, "Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_API_FAILURE))
        }
    }
    //getWeatherDataSync with refresh true from cloud with other fail return check  if weatherLiveDataLiveData is updated
    @Test
    fun getWeatherDataSyncWithRefreshTrue_otherFail_WeatherLiveDataIsUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.UNKONWN_EXCEPTION
            SUT.getWeatherDataSync(true,"Cairo")
            assertThat(SUT.weatherLiveData.value, `is`(RESPONSE_OTHER_FAILURE))
        }
    }
    //getWeatherDataSync with refresh true from cloud with success check if weatherData model is sent to dbManager
    @Test
    fun getWeatherDataSyncWithRefreshTrue_success_dbManagerIsUpdated() {
        testCoroutineRule.runBlockingTest {
            cloudTD.status = Status.SUCCESS
            SUT.getWeatherDataSync(true, "Cairo")
            assertThat(dbManagerTD.weatherUpdated, `is`(WEATHER))
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
    //working with database will use Couroutines and suspend functions
    //saveWeatherData to check if data is saved
    @Test
    fun saveWeatherData_weatherDataIsSaved() {
        testCoroutineRule.runBlockingTest{
            SUT.saveWeatherData(WEATHER)
            assertThat(dbManagerTD.getWeather(), `is`(WEATHER))
        }
    }
    //updateWeatherData to check if data is updated
    @Test
    fun updateWeatherData_weatherDataIsUpdated() {
        testCoroutineRule.runBlockingTest {
            SUT.updateWeatherData(WEATHER)
            assertThat(dbManagerTD.getWeather(), `is`(WEATHER))
        }
    }
    //deleteWeatherData to check if data is deleted
    @Test
    fun deleteWeatherData_weatherDataIsDeleted() {
        testCoroutineRule.runBlockingTest {
            SUT.deleteWeatherData(WEATHER)
            assertNull(dbManagerTD.getWeather())
        }
    }
    //---------------------------------------------------------------------
    //-----------------Helper Classes--------------------------------------


    private class HomeCloudTD: HomeServiceImpl {

        var status: Status? = null
        var city: String? = null



        override fun getWeatherForCity(city: String):BaseResponseModel<WeatherData> {
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
        var weatherUpdated: WeatherData? = null
        var weatherData: WeatherData? = null

        override suspend fun saveWeather(weather: WeatherData) {
            this.weatherData = weather
        }

        override suspend fun updateWeather(weather: WeatherData) {
            weatherUpdated = weather
        }

        override suspend fun getWeather(): WeatherData? {
            return if(!isDataDeleted)
                WEATHER
            else
                null
        }

        override suspend fun deleteWeather(weather: WeatherData) {
            isDataDeleted = true
        }
    }

    /*private class HomePreferenceManagerTD: PreferenceManager{
        override fun saveCity(country: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCity(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }*/
}