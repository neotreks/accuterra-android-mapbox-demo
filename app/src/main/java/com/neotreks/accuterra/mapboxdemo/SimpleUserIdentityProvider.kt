package com.neotreks.accuterra.mapboxdemo

import android.content.Context
import com.neotreks.accuterra.mobile.sdk.IIdentityProvider

class SimpleUserIdentityProvider: IIdentityProvider {
    override suspend fun getUserId(context: Context): String {
        return "test driver uuid"
    }
}