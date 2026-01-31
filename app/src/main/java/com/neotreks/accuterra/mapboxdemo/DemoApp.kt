package com.neotreks.accuterra.mapboxdemo

import android.app.Application
import com.mapbox.common.MapboxOptions

class DemoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MapboxOptions.accessToken = BuildConfig.MAPBOX_TOKEN
    }
}