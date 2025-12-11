package com.neotreks.accuterra.mapboxdemo

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.mapbox.maps.EdgeInsets
import com.mapbox.maps.MapView
import com.mapbox.maps.plugin.scalebar.scalebar
import com.mapbox.maps.toCameraOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private val mapView: MapView by lazy { findViewById(R.id.mapView) }
    private val buttonsContainer: ViewGroup by lazy { findViewById(R.id.buttons_container) }
    private val initSdkButton: MaterialButton by lazy { findViewById(R.id.initialize_sdk_button) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        initEdgeToEdgeMapInsets()
        initButtons()
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

    private fun initButtons() {
        // Make sure the buttons do not overlap with the system bars
        ViewCompat.setOnApplyWindowInsetsListener(buttonsContainer) { _, insets ->
            val systemInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            buttonsContainer.setPadding(systemInsets.left, systemInsets.top, 0, 0)
            insets
        }

        // Set button click handlers
        initSdkButton.setOnClickListener {
            initSdkAsync()
        }
    }

    private fun initSdkAsync() {
        initSdkButton.isEnabled = false

        lifecycleScope.launch(Dispatchers.Main) {
            val sdkInitResult = SdkInitializer().initialize(applicationContext)

            ensureActive()

            if (sdkInitResult.isSuccess && sdkInitResult.value == true) {
                onSdkInitSuccess()
            } else {
                onSdkInitFailure(
                    sdkInitResult.error ?: RuntimeException("Unknown SDK init error")
                )
            }
        }
    }

    private fun onSdkInitSuccess() {
        Log.i(TAG, "AccuTerra SDK is ready")
        Toast.makeText(this, "SDK Initialized", Toast.LENGTH_SHORT).show()
    }

    private fun onSdkInitFailure(error: Throwable) {
        Log.e(TAG, "AccuTerra SDK Initialization Error", error)
        Toast.makeText(this, "SDK Initialization Error", Toast.LENGTH_SHORT).show()
    }
}
