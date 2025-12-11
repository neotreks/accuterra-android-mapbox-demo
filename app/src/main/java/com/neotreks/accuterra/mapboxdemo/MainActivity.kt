package com.neotreks.accuterra.mapboxdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.toCameraOptions

class MainActivity : AppCompatActivity() {

    private val mapView: MapView by lazy { findViewById(R.id.mapView) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initEdgeToEdgeMapInsets()
    }

    private fun initEdgeToEdgeMapInsets() {

        ViewCompat.setOnApplyWindowInsetsListener(mapView) { _, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            // Convert Android Insets to Mapbox EdgeInsets
            val mapboxInsets = EdgeInsets(
                systemInsets.top.toDouble(),
                systemInsets.left.toDouble(),
                systemInsets.bottom.toDouble(),
                systemInsets.right.toDouble()
            )

            // Apply these insets as padding to the Mapbox map camera
            mapView.mapboxMap.setCamera(
                mapView.mapboxMap.cameraState.toCameraOptions().toBuilder()
                    .padding(mapboxInsets)
                    .build()
            )

            // Adjust the margins for the scalebar
            mapView.scalebar.marginBottom = systemInsets.bottom.toFloat()
            mapView.scalebar.marginRight = (systemInsets.bottom + systemInsets.right).toFloat()

            // Let other listeners handle the insets
            insets
        }

    }
}