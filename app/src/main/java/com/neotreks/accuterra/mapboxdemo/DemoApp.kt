package com.neotreks.accuterra.mapboxdemo

import android.app.Application

class DemoApp : Application() {
    override fun onCreate() {
        super.onCreate()

        MapLibreInitializer().init(this)
    }
}