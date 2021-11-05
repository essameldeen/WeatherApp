package com.example.weatherapp.viewModel

import com.example.weatherapp.module.WeatherData

sealed class WeatherViewState {
    object LoadingState : WeatherViewState()
    data class Result(val weatherData: WeatherData) : WeatherViewState()
    data class Error(val errorMessage: String) : WeatherViewState()

}