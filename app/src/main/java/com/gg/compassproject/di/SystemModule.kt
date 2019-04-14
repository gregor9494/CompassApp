package com.gg.compassproject.di

import com.gg.compassproject.platform.permissions.PermissionDispatcher
import org.koin.dsl.module

val systemModule = module {
    single {
        PermissionDispatcher(
            get()
        )
    }
}