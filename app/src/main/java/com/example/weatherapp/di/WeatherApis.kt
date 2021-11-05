package com.example.weatherapp.di

import com.example.weatherapp.module.WeatherData
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApis {

    @GET("weather")
    suspend fun getWeatherByName(@Query("q") name: String): WeatherData

    @GET("weather")
    suspend fun getWeatherByCurrentLocation(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherData
}