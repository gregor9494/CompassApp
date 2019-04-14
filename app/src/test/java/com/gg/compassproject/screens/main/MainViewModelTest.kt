package com.gg.compassproject.screens.main

import android.hardware.SensorEvent
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gg.compassproject.AndroidTest
import com.gg.compassproject.platform.location.LocationCreator
import com.gg.compassproject.platform.location.LocationProvider
import com.gg.compassproject.platform.sensor.SensorDataProvider
import com.gg.compassproject.platform.sensor.SensorEventCreator
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.amshove.kluent.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock

/**
 * Created by ggaworowski on 12.04.2019.
 */
class MainViewModelTest : AndroidTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var sensorDataProvider: SensorDataProvider

    @Mock
    lateinit var locationProvider: LocationProvider

    @Before
    fun setup() {
        mainViewModel = MainViewModel(sensorDataProvider, locationProvider)
    }

    @Test
    fun `test sensor events has subscription after start listening`() {
        val sensorEventObservable = PublishSubject.create<SensorEvent>()
        val locationObservable = PublishSubject.create<Location>()
        whenever(sensorDataProvider.listenEvents()).thenReturn(sensorEventObservable)
        whenever(locationProvider.requestLocation()).thenReturn(locationObservable)
        val test = sensorEventObservable.test()
        mainViewModel.startListening()
        test.hasSubscription() shouldBe true
    }

    @Test
    fun `test location has subscription after start listening`() {
        val sensorEventObservable = PublishSubject.create<SensorEvent>()
        val locationObservable = PublishSubject.create<Location>()
        whenever(sensorDataProvider.listenEvents()).thenReturn(sensorEventObservable)
        whenever(locationProvider.requestLocation()).thenReturn(locationObservable)
        val test = locationObservable.test()
        mainViewModel.startListening()
        test.hasSubscription() shouldBe true
    }

    @Test
    fun `test is permission denied after user rejection`() {
        whenever(sensorDataProvider.listenEvents()).thenReturn(PublishSubject.create<SensorEvent>())
        whenever(locationProvider.requestLocation()).thenReturn(Observable.error(SecurityException()))
        mainViewModel.startListening()
        mainViewModel.isPermissionDenied.value shouldBe true
    }

    @Test
    fun `test is permission is not denied after user acceptation`() {
        whenever(sensorDataProvider.listenEvents()).thenReturn(PublishSubject.create<SensorEvent>())
        whenever(locationProvider.requestLocation()).thenReturn(PublishSubject.create<Location>())
        mainViewModel.startListening()
        mainViewModel.isPermissionDenied.value shouldBe false
    }

    @Test
    fun `test is routing when all coordinates provided`() {
        whenever(sensorDataProvider.listenEvents()).thenReturn(Observable.just(SensorEventCreator.createSensorEvent(9.0f)))
        whenever(locationProvider.requestLocation()).thenReturn(Observable.just(LocationCreator.create(40.0, 40.0)))
        mainViewModel.targetLatitude.value = "20.0"
        mainViewModel.targetLongitude.value = "23.0"
        mainViewModel.startListening()
        mainViewModel.isRouting.value shouldBe true
    }

    @Test
    fun `test routing is off when coordinates are not provided`() {
        whenever(sensorDataProvider.listenEvents()).thenReturn(Observable.just(SensorEventCreator.createSensorEvent(9.0f)))
        whenever(locationProvider.requestLocation()).thenReturn(Observable.just(LocationCreator.create(40.0, 40.0)))
        mainViewModel.targetLatitude.value = ""
        mainViewModel.targetLongitude.value = ""
        mainViewModel.startListening()
        mainViewModel.isRouting.value shouldBe false
    }
}