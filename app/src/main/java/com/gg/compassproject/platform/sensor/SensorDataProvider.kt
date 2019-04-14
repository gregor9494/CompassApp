package com.gg.compassproject.platform.sensor

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class SensorDataProvider(private val sensorManager: SensorManager) : SensorEventListener {

    private val sensorEventStream = BehaviorSubject.create<SensorEvent>()

    fun listenEvents(): Observable<SensorEvent> = sensorEventStream
        .doOnSubscribe { startListen() }
        .doOnDispose { stopListen() }

    private fun startListen() {
        val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
    }

    private fun stopListen() {
        sensorManager.unregisterListener(this)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        sensorEventStream.onNext(event)
    }

}