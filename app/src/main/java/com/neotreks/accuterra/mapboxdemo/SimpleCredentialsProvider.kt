package com.neotreks.accuterra.mapboxdemo

import android.content.Context
import com.neotreks.accuterra.mobile.sdk.ICredentialsAccessProvider
import com.neotreks.accuterra.mobile.sdk.security.model.ClientCredentials

internal class SimpleCredentialsProvider : ICredentialsAccessProvider {
    override suspend fun getClientCredentials(context: Context): ClientCredentials {
        return ClientCredentials(
            clientId = BuildConfig.WS_AUTH_CLIENT_ID,
            clientSecret = BuildConfig.WS_AUTH_CLIENT_SECRET,
        )
    }
}