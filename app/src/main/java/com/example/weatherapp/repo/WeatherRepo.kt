package com.example.weatherapp.repo

import com.example.weatherapp.di.WeatherApis
import com.example.weatherapp.module.WeatherData

class WeatherRepo(private val weatherApis: WeatherApis) : IWeatherRepo {
    override suspend fun getWeatherByName(name: String): WeatherData {
        return weatherApis.getWeatherByName(name)
    }

    override suspend fun getWeatherByCurrentLocation(lat: Double, lon: Double): WeatherData {
        return weatherApis.getWeatherByCurrentLocation(lat, lon)
    }

}