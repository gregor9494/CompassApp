package com.gg.compassproject.platform.location

import android.content.Context
import android.location.Location
import com.gg.compassproject.UnitTest
import com.gg.compassproject.platform.permissions.PermissionDispatcher
import com.gun0912.tedpermission.TedPermissionResult
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.Single
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import pl.charmas.android.reactivelocation2.ReactiveLocationProvider

/**
 * Created by ggaworowski on 13.04.2019.
 */
class LocationProviderTest : UnitTest() {
    lateinit var locationProvider: LocationProvider

    @Mock
    lateinit var permissionDispatcher: PermissionDispatcher

    @Mock
    lateinit var reactiveLocationProvider: ReactiveLocationProvider

    @Before
    fun setup() {
        locationProvider = LocationProvider(mock(Context::class), permissionDispatcher, reactiveLocationProvider)
    }

    @Test
    fun `test request location when no permission`() {
        whenever(reactiveLocationProvider.lastKnownLocation).thenReturn(Observable.just(mock(Location::class)))
        whenever(permissionDispatcher.requestLocationPermissions()).thenReturn(Single.error(SecurityException()))
        locationProvider.requestLocation().test().assertError { it is SecurityException }
    }

    @Test
    fun `test request location when has permission and location`() {
        whenever(reactiveLocationProvider.lastKnownLocation).thenReturn(Observable.just(mock(Location::class)))
        val permissionResult = Single.just(TedPermissionResult(emptyList()))
        whenever(permissionDispatcher.requestLocationPermissions()).thenReturn(permissionResult)
        val locationObservable = Observable.just(mock(Location::class))
        whenever(reactiveLocationProvider.getUpdatedLocation(any())).thenReturn(locationObservable)
        locationProvider.requestLocation().test().assertValueCount(2) // Start with last known location
    }
}