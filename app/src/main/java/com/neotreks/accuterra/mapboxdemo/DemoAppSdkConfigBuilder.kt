package com.neotreks.accuterra.mapboxdemo

import com.neotreks.accuterra.mobile.sdk.ApkSdkConfig
import com.neotreks.accuterra.mobile.sdk.security.model.SdkEndpointConfig
import com.neotreks.accuterra.mobile.sdk.trail.model.TrailConfiguration
import com.neotreks.accuterra.mobile.sdk.trail.model.TrailFeatureCacheMode

internal class DemoAppSdkConfigBuilder {

    private var sdkEndpointConfig = SdkEndpointConfig(
        wsBaseUrl = BuildConfig.WS_BASE_URL,
        wsAuthUrl = BuildConfig.WS_AUTH_URL,
    )

    private var trailConfiguration = TrailConfiguration(
        // Increases launch time
        trailFeatureCacheMode = TrailFeatureCacheMode.CACHE_TRAILS_DURING_SDK_INIT,
        // Update trail DB during SDK initialization
        updateTrailDbDuringSdkInit = false,
        // Update trail User Data during SDK initialization
        updateTrailUserDataDuringSdkInit = false,
        // Update trail Dynamic Data during SDK initialization (ratings, reported closed dates, etc.)
        updateTrailDynamicDataDuringSdkInit = false,
    )

    fun build() : ApkSdkConfig {
        return ApkSdkConfig(
            sdkEndpointConfig = sdkEndpointConfig,
            trailConfiguration = trailConfiguration,
        )
    }
}