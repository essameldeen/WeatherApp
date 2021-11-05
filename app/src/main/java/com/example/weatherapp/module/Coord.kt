package com.example.weatherapp.module


import com.google.gson.annotations.SerializedName


data class Coord(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
)