package com.neotreks.accuterra.mapboxdemo

import android.content.Context
import com.mapbox.common.MapboxOptions
import org.maplibre.android.MapLibre
import org.maplibre.android.WellKnownTileServer

class MapLibreInitializer {
    fun init(context: Context) {
        MapLibre.getInstance(
            context.applicationContext,
            BuildConfig.MAPBOX_TOKEN,
            WellKnownTileServer.Mapbox
        )
        MapboxOptions.accessToken = BuildConfig.MAPBOX_TOKEN
    }
}