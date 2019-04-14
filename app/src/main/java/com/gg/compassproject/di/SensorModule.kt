package com.gg.compassproject.di

import android.content.Context.SENSOR_SERVICE
import android.hardware.SensorManager
import com.gg.compassproject.platform.sensor.SensorDataProvider
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val sensorModule = module {
    single { SensorDataProvider(get()) }
    single { androidContext().getSystemService(SENSOR_SERVICE) as SensorManager }

}