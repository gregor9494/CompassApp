package com.gg.compassproject.platform.location

import android.location.Location
import android.location.LocationManager

object LocationCreator {
    fun create(latitude: Double, longitude: Double) : Location {
        val location = Location(LocationManager.GPS_PROVIDER)
        location.latitude = latitude
        location.longitude = longitude
        return location
    }
}