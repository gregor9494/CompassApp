package com.gg.compassproject.app

import android.app.Application
import com.gg.compassproject.BuildConfig
import com.gg.compassproject.di.KoinModules
import com.squareup.leakcanary.LeakCanary

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CompassApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        this.initializeLeakDetection()
        this.initInjection()
    }

    private fun initInjection() = startKoin {
        androidContext(this@CompassApplication)
        modules(KoinModules.modules)
    }

    private fun initializeLeakDetection() {
        if (BuildConfig.DEBUG) LeakCanary.install(this)
    }
}
