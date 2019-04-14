package com.gg.compassproject.platform.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.gg.compassproject.platform.permissions.PermissionDispatcher
import com.google.android.gms.location.LocationRequest
import io.reactivex.Observable
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider

@SuppressLint("MissingPermission")
class LocationProvider(
    context: Context,
    private val permissionDispatcher: PermissionDispatcher,
    private val reactiveLocationProvider: ReactiveLocationProvider = ReactiveLocationProvider(context)
) {
    fun requestLocation(): Observable<Location> {
        return permissionDispatcher.requestLocationPermissions()
            .flatMapObservable { reactiveLocationProvider.getUpdatedLocation(createRequest()) }
            .startWith(lastKnownLocation())
    }

    private fun lastKnownLocation() = reactiveLocationProvider.lastKnownLocation

    private fun createRequest(): LocationRequest {
        return LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
            .setInterval(1000)
    }
}