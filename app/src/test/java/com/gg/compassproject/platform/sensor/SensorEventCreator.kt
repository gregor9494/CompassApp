package com.gg.compassproject.platform.sensor

import android.hardware.SensorEvent
import org.mockito.Mockito

object SensorEventCreator {
    fun createSensorEvent(value: Float): SensorEvent {
        val sensorEvent = Mockito.mock(SensorEvent::class.java)

        try {
            val valuesField = SensorEvent::class.java.getField("values")
            valuesField.isAccessible = true
            val sensorValue = floatArrayOf(value, value, value, value, value, value, value, value, value)
            try {
                valuesField.set(sensorEvent, sensorValue)
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }

        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        }

        return sensorEvent
    }
}
