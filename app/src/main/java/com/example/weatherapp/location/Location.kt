package com.example.weatherapp.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class Location(private val activity: AppCompatActivity, private val iLocation: CurrentLocation) {

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(activity)

    @SuppressLint("MissingPermission")
    private val requestPermission =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    iLocation.currentLocation(it)
                }
            }
        }
    init {
        showLocationPreview()
    }

    private fun showLocationPreview() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener {
                    iLocation.currentLocation(it)
                }

        } else requestLocationPermissions()
    }

    private fun requestLocationPermissions() {
        requestPermission.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        requestPermission.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }


}

interface CurrentLocation {
    fun currentLocation(location: Location?)
}