package com.example.weatherapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.location.CurrentLocation
import com.example.weatherapp.location.Location
import com.example.weatherapp.viewModel.WeatherViewModel
import com.example.weatherapp.viewModel.WeatherViewState
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {
    private val viewModel: WeatherViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        currentLocation()

        viewModel.state.observe(this, Observer {
            renderView(it)
        })

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                viewModel.getWeatherByName(query!!)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }


        })
        binding.currentLocationBtn.setOnClickListener {
            currentLocation()
        }


    }

    private fun renderView(view: WeatherViewState?) {
        when (view) {
            is WeatherViewState.LoadingState -> binding.progress.visibility = View.VISIBLE
            is WeatherViewState.Error -> {
                binding.progress.visibility = View.INVISIBLE
                Toast.makeText(this, view.errorMessage, Toast.LENGTH_LONG).show()
            }
            is WeatherViewState.Result -> {
                binding.progress.visibility = View.INVISIBLE
                val weatherCondition = view.weatherData
                binding.cityNameTv.text = weatherCondition.name
                (weatherCondition.main?.temp.toString() + " C").also { binding.tempTv.text = it }
                (weatherCondition.weather?.get(0)?.main + ", " + weatherCondition.weather!![0]?.description).also {
                    binding.weatherStatueTv.text = it
                }
            }
        }

    }

    private fun currentLocation() {
        Location(this, object : CurrentLocation {
            override fun currentLocation(location: android.location.Location?) {
                location?.let {
                    viewModel.getWeatherByCurrentLocation(
                        it.latitude,
                        it.longitude
                    )
                }
            }

        })
    }

}