package com.gg.compassproject.di


import com.gg.compassproject.platform.location.LocationProvider
import org.koin.dsl.module

val locationModule = module {
    single { LocationProvider(get(),get()) }
}