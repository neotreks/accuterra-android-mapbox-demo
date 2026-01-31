package com.neotreks.accuterra.mapboxdemo

import android.content.Context
import com.neotreks.accuterra.mobile.sdk.SdkManager
import com.neotreks.accuterra.mobile.sdk.model.Result as SdkResult

class SdkInitializer {
    suspend fun initialize(
        context: Context,
    ) : SdkResult<Boolean> {
        val config = DemoAppSdkConfigBuilder().build()
        val accessProvider = SimpleCredentialsProvider()
        val userIdentityProvider = SimpleUserIdentityProvider()
        val initListener = LoggingListener()

        return SdkManager.initSdk(
            context = context.applicationContext,
            config = config,
            accessProvider = accessProvider,
            identityProvider = userIdentityProvider,
            listener = initListener,
            mapProvider = null
        )
    }
}