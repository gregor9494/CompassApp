package com.gg.compassproject.platform.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.gg.compassproject.UnitTest
import com.gg.compassproject.platform.sensor.SensorDataProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mock

/**
 * Created by ggaworowski on 13.04.2019.
 */
class SensorDataProviderTest : UnitTest() {
    lateinit var sensorDataProvider: SensorDataProvider

    @Mock
    lateinit var sensorManager: SensorManager

    @Before
    fun setup() {
        sensorDataProvider = SensorDataProvider(sensorManager)
    }

    @Test
    fun `test new event will notify observer`() {
        val event = mock(SensorEvent::class)
        val testObserver = sensorDataProvider.listenEvents().test()
        sensorDataProvider.onSensorChanged(event)
        testObserver.assertValueCount(1)
    }

    @Test
    fun `test subscribe observer will start event listening`() {
        whenever(sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)).thenReturn(mock(Sensor::class))
        sensorDataProvider.listenEvents().subscribe()
        verify(sensorManager, times(1)).registerListener(any<SensorEventListener>(), any(), any())
    }

    @Test
    fun `test dispose observer will stop event listening`() {
        whenever(sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)).thenReturn(mock(Sensor::class))
        sensorDataProvider.listenEvents().subscribe().dispose()
        verify(sensorManager, times(1)).unregisterListener(any<SensorEventListener>())
    }
}