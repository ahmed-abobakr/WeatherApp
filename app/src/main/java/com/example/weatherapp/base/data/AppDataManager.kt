package com.example.weatherapp.base.data

class AppDataManager(val apiManager: APIManager, val dbManager: DbManager, val preferenceManager: PreferenceManager)
    : DataManager{

    override fun saveWeather() {
        dbManager.saveWeather()
    }

    override fun updateWeather() {
        dbManager.updateWeather()
    }

    override fun getWeather() {
        dbManager.getWeather()
    }

    override fun deleteWeather() {
        dbManager.deleteWeather()
    }

    override fun saveCountry(country: String) {
        preferenceManager.saveCountry(country)
    }

    override fun getCountry(): String {
        return preferenceManager.getCountry()
    }

    override fun mapException(throwable: Throwable): Throwable {
        return apiManager.mapException(throwable)
    }

    override fun <T> execute(cls: Class<T>): T {
        return apiManager.execute(cls)
    }
}