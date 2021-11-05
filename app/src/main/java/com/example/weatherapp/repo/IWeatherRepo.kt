package com.example.weatherapp.repo

import com.example.weatherapp.module.WeatherData

interface IWeatherRepo {

    suspend fun getWeatherByName(name: String): WeatherData
    suspend fun getWeatherByCurrentLocation(lat: Double, lon: Double): WeatherData
}