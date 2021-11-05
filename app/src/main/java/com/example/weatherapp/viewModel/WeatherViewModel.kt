package com.example.weatherapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.repo.IWeatherRepo
import kotlinx.coroutines.launch

class WeatherViewModel(private val repo: IWeatherRepo) : ViewModel() {
    private val _state = MutableLiveData<WeatherViewState>(WeatherViewState.LoadingState)
    var state: LiveData<WeatherViewState> = _state


    fun getWeatherByName(name: String) {
        _state.value = WeatherViewState.LoadingState
        viewModelScope.launch {
            try {
                val result = repo.getWeatherByName(name)
                result?.let {
                    _state.value = WeatherViewState.Result(result)
                }
            } catch (e: Exception) {
                _state.value = e.message?.let { WeatherViewState.Error(it) }
            }
        }


    }

    fun getWeatherByCurrentLocation(lat: Double, lon: Double) {
        _state.value = WeatherViewState.LoadingState
        viewModelScope.launch {
            try {
                val result = repo.getWeatherByCurrentLocation(lat, lon)
                result?.let {
                    _state.value = WeatherViewState.Result(result)
                }
            } catch (e: Exception) {
                _state.value = e.message?.let { WeatherViewState.Error(it) }
            }
        }


    }


}