package com.gg.compassproject.screens.main

import android.location.Location
import android.location.LocationManager
import androidx.lifecycle.MutableLiveData
import com.gg.compassproject.core.platform.BaseViewModel
import com.gg.compassproject.data.direction.DirectionParser
import com.gg.compassproject.platform.location.LocationProvider
import com.gg.compassproject.platform.sensor.SensorDataProvider
import java.util.concurrent.TimeUnit

class MainViewModel(
    private val sensorDataProvider: SensorDataProvider,
    private val locationProvider: LocationProvider
) : BaseViewModel() {

    val arrowRotation = MutableLiveData<Float>().apply { value = 0.0f }
    val compassRotation = MutableLiveData<Float>().apply { value = 0.0f }
    val targetLatitude = MutableLiveData<String>()
    val targetLongitude = MutableLiveData<String>()
    val isPermissionDenied = MutableLiveData<Boolean>().apply { value = false }
    val isRouting = MutableLiveData<Boolean>().apply { value = false }

    private val directionParser = DirectionParser()

    fun startListening() {
        listenSensorEvents()
        listenLocation()
    }

    private fun listenLocation() {
        addSubscription(
            locationProvider.requestLocation()
                .subscribe({
                    directionParser.location = it
                    updateDirections()
                }, {
                    if (it is SecurityException) isPermissionDenied.value = true
                    it.printStackTrace()
                })
        )
    }

    private fun listenSensorEvents() {
        addSubscription(
            sensorDataProvider.listenEvents()
                .throttleFirst(100, TimeUnit.MILLISECONDS)
                .subscribe({
                    directionParser.sensorEvent = it
                    updateDirections()
                }, { it.printStackTrace() })
        )
    }

    fun askForLocationAgain() {
        isPermissionDenied.value = false
        listenLocation()
    }

    private fun updateDirections() {
        updateDestination()
        val direction = directionParser.getDirection()
        compassRotation.value = direction.northPoleDirectionRotation?.toFloat() ?: 0.0f
        arrowRotation.value = direction.selectedPointDirectionRotation?.toFloat() ?: 0.0f
    }

    private fun updateDestination() {
        val longitude = targetLongitude.value?.toDoubleOrNull()
        val latitude = targetLatitude.value?.toDoubleOrNull()
        if (longitude != null && latitude != null) {
            isRouting.value = true
            val location = Location(LocationManager.GPS_PROVIDER)
            location.latitude = latitude
            location.longitude = longitude
            directionParser.destination = location
        } else {
            directionParser.destination = null
            isRouting.value = false
        }
    }

}